package per.zongwlee.issue.infra.utils;

import io.choerodon.core.domain.Page;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/04/14
 */
public class ConvertUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

    private ConvertUtil() {
    }

    public static <T> Page<T> convertPage(Page pageSource, Class<T> destin) {
        Page<T> pageBack = new Page<>();
        pageBack.setNumber(pageSource.getNumber());
        pageBack.setNumberOfElements(pageSource.getNumberOfElements());
        pageBack.setSize(pageSource.getSize());
        pageBack.setTotalElements(pageSource.getTotalElements());
        pageBack.setTotalPages(pageSource.getTotalPages());
        if (pageSource.getContent().isEmpty()) {
            return pageBack;
        } else {
            List<T> list = new ArrayList<>(pageSource.getContent().size());
            Iterator var6 = pageSource.getContent().iterator();

            while(var6.hasNext()) {
                Object object = var6.next();
                T t = modelMapper.map(object,destin);
                list.add(t);
            }

            pageBack.setContent(list);
            return pageBack;
        }
    }
}
