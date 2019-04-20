package per.zongwlee.oauth.api.service.impl;

import io.choerodon.core.convertor.ConvertPageHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.gitlab4j.api.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.zongwlee.oauth.api.dto.AccessToken;
import per.zongwlee.oauth.api.dto.ReturnRoleDTO;
import per.zongwlee.oauth.api.dto.RoleDTO;
import per.zongwlee.oauth.api.service.RoleService;
import per.zongwlee.oauth.api.validator.UserValidator;
import per.zongwlee.oauth.domain.entity.RoleE;
import per.zongwlee.oauth.infra.common.util.JwtTokenUtils;
import per.zongwlee.oauth.infra.feign.DevopsFeignClient;
import per.zongwlee.oauth.infra.mapper.RoleMapper;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private static final String USERNAMECANNOTBENULL = "error.name.can.not.be.empty";

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    DevopsFeignClient devopsFeignClient;

    @Autowired
    private UserValidator userValidator;

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public void setUserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @Override
    public AccessToken login(RoleDTO roleDTO) {
        if (roleDTO.getName() == null) {
            throw new CommonException(USERNAMECANNOTBENULL);
        }
        RoleE roleE = new RoleE();
        roleE.setName(roleDTO.getName());
        RoleE res = roleMapper.selectOne(roleE);
        if (bCryptPasswordEncoder.matches(roleDTO.getPassword(), res.getPassword())) {
            AccessToken accessToken = JwtTokenUtils.createToken(res.getName(), res.getStringType(), roleDTO.getIsRememberMe());
            accessToken.setUserId(res.getId());
            accessToken.setGitlabUserId(res.getGitlabId());
            return accessToken;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnRoleDTO register(RoleDTO roleDTO) {
        //gitlab创建用户
        User user = new User();
        user.setName(roleDTO.getName());
        user.setUsername(roleDTO.getName() + "-gitlab");
        user.setEmail(roleDTO.getEmail());
        Integer gitlabId = devopsFeignClient.create(roleDTO.getPassword(), 1, user).getBody().getId();

        if (roleDTO.getName() == null) {
            throw new CommonException(USERNAMECANNOTBENULL);
        }
        roleDTO.setType(0);
        RoleE roleEName = new RoleE();
        roleEName.setName(roleDTO.getName());
        RoleE resName = roleMapper.selectOne(roleEName);
        if (null != resName) {
            throw new CommonException("error.name.already.exist");
        }
        RoleE roleE = modelMapper.map(roleDTO, RoleE.class);
        roleE.setGitlabId(gitlabId);
        roleE.setPassword(bCryptPasswordEncoder.encode(roleE.getPassword()));
        if (roleMapper.insert(roleE) != 1) {
            throw new CommonException("error.user.insert");
        }
        return modelMapper.map(roleMapper.selectOne(roleE), ReturnRoleDTO.class);
    }

    @Override
    public ReturnRoleDTO updateSelective(RoleDTO roleDTO) {
        RoleE roleE = modelMapper.map(roleDTO, RoleE.class);
        if (roleMapper.updateByPrimaryKeySelective(roleE) != 1) {
            throw new CommonException("error.role.update");
        }
        ReturnRoleDTO res = modelMapper.map(roleMapper.selectOne(roleE), ReturnRoleDTO.class);
        res.loadRoleType();
        return res;
    }

    @Override
    public boolean checkEmail(String email) {
        RoleE roleEEmail = new RoleE();
        if (!userValidator.emailValidator(email)) {
            throw new CommonException("error.email.format");
        }
        roleEEmail.setEmail(email);
        RoleE resEmail = roleMapper.selectOne(roleEEmail);
        return null == resEmail;
    }

    @Override
    public ReturnRoleDTO queryById(Long roleId) {
        ReturnRoleDTO res = modelMapper.map(roleMapper.selectByPrimaryKey(roleId), ReturnRoleDTO.class);
        res.loadRoleType();
        return res;
    }

    @Override
    public Page<ReturnRoleDTO> pageQuery(PageRequest pageRequest) {
        Page<ReturnRoleDTO> roleEPage = ConvertPageHelper.convertPage(PageHelper.doPageAndSort(
                pageRequest, () -> roleMapper.selectAll()), ReturnRoleDTO.class);
        roleEPage.getContent().forEach(ReturnRoleDTO::loadRoleType);
        return roleEPage;
    }

    @Override
    public boolean checkAuthorazition(String jwtToken) {
        return !JwtTokenUtils.isExpiration(jwtToken);
    }

    @Override
    public ReturnRoleDTO getUserByAuthorazition(String jwtToken) {
        if (JwtTokenUtils.isExpiration(jwtToken)) {
            throw new CommonException("error.user.token.has.expired");
        }
        RoleE roleE = new RoleE();
        roleE.setName(JwtTokenUtils.getUsername(jwtToken));
        return modelMapper.map(roleMapper.selectOne(roleE), ReturnRoleDTO.class);
    }

}
