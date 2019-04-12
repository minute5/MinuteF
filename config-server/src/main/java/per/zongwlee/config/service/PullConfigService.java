package per.zongwlee.config.service;

import per.zongwlee.config.domain.Config;

/**
 * @author flyleft
 */

public interface PullConfigService {

    Config getConfig(String serviceName, String configVersion);

}
