package per.zongwlee.oauth.api.service.impl;

import io.choerodon.core.exception.CommonException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import per.zongwlee.oauth.api.dto.AccessToken;
import per.zongwlee.oauth.api.dto.RoleDTO;
import per.zongwlee.oauth.api.service.RoleService;
import per.zongwlee.oauth.api.validator.UserValidator;
import per.zongwlee.oauth.domain.entity.RoleE;
import per.zongwlee.oauth.infra.common.util.JwtTokenUtils;
import per.zongwlee.oauth.infra.mapper.RoleMapper;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private static final String USERNAMECANNOTBENULL = "error.name.can.not.be.empty";

    @Autowired
    private RoleMapper roleMapper;

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
        if(bCryptPasswordEncoder.matches(roleDTO.getPassword(),res.getPassword())){
            return JwtTokenUtils.createToken(res.getName(),res.getStringType(),roleDTO.getIsRememberMe());
        }
        return null;
    }

    @Override
    public String register(RoleDTO roleDTO) {
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

        ModelMapper modelMapper = new ModelMapper();
        RoleE roleE = modelMapper.map(roleDTO, RoleE.class);
        roleE.setPassword(bCryptPasswordEncoder.encode(roleE.getPassword()));
        if (roleMapper.insert(roleE) != 1) {
            throw new CommonException( "error.user.insert");
        }
        return "success";
    }

    @Override
    public RoleE updateSelective(RoleDTO roleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        RoleE roleE = modelMapper.map(roleDTO, RoleE.class);
        if (roleMapper.updateByPrimaryKeySelective(roleE) != 1) {
            throw new CommonException("error.role.update");
        }
        return roleMapper.selectByPrimaryKey(roleE.getId());
    }

    @Override
    public boolean checkEmail(String email) {
        RoleE roleEEmail = new RoleE();
        if(!userValidator.emailValidator(email)){
            throw new CommonException("error.email.format");
        }
        roleEEmail.setEmail(email);
        RoleE resEmail = roleMapper.selectOne(roleEEmail);
        return null == resEmail;
    }

    @Override
    public boolean checkAuthorazition(String jwtToken) {
        return !JwtTokenUtils.isExpiration(jwtToken);
    }
}
