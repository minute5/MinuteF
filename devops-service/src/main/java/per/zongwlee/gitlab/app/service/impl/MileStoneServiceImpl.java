package per.zongwlee.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.MileStonesApi;
import org.gitlab4j.api.models.Milestone;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import per.zongwlee.gitlab.api.dto.MileStoneDto;
import per.zongwlee.gitlab.app.service.MileStoneService;
import per.zongwlee.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class MileStoneServiceImpl implements MileStoneService {

    private Gitlab4jClient gitlab4jclient;

    public MileStoneServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public Milestone createMilestone(MileStoneDto mileStoneDto) {
        try {
            return gitlab4jclient
                    .getGitLabApi()
                    .getMileStonesApi()
                    .createMilestone(mileStoneDto.getProjectId(),
                            mileStoneDto.getTitle(),
                            mileStoneDto.getDescription(),
                            mileStoneDto.getDueDate(),
                            mileStoneDto.getStartDate());
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Milestone closeMilestone(Integer projectId, Integer milestoneId) {
        try {
            return gitlab4jclient
                    .getGitLabApi()
                    .getMileStonesApi()
                    .closeMilestone(projectId, milestoneId);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);

        }
    }

    @Override
    public Milestone activeMilestone(Integer projectId, Integer milestoneId) {
        try {
            return gitlab4jclient
                    .getGitLabApi()
                    .getMileStonesApi()
                    .activateMilestone(projectId, milestoneId);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);

        }
    }

    @Override
    public Milestone updateMilestone(MileStoneDto mileStoneDto) {
        try {
            return gitlab4jclient
                    .getGitLabApi()
                    .getMileStonesApi()
                    .updateMilestone(mileStoneDto.getProjectId(),
                            mileStoneDto.getMilestoneId(),
                            mileStoneDto.getTitle(),
                            mileStoneDto.getDescription(),
                            mileStoneDto.getDueDate(),
                            mileStoneDto.getStartDate(),
                            mileStoneDto.getMilestoneState());
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Milestone> listMilestones(Integer projectId, Integer page, Integer perPage) {
        try {
            MileStonesApi mileStonesApi = gitlab4jclient.getGitLabApi(null).getMileStonesApi();
            if (projectId != null) {
                return page == null || perPage == null
                        ? mileStonesApi.getMilestones(projectId)
                        : mileStonesApi.getMilestones(projectId, page, perPage);
            } else {
                throw new FeignException("error.milestones.query");
            }
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }

    }

    @Override
    public List<Milestone> listMileStoneByOptions(MileStoneDto mileStoneDto) {
        MileStonesApi mileStonesApi = gitlab4jclient.getGitLabApi(null).getMileStonesApi();
        try {
            if (mileStoneDto.getProjectId() != null) {
                if (mileStoneDto.getMilestoneState() != null) {
                    if (mileStoneDto.getSearch() != null) {
                        return mileStonesApi.getMilestones(
                                mileStoneDto.getProjectId(),
                                mileStoneDto.getMilestoneState(),
                                mileStoneDto.getSearch());
                    } else {
                        return mileStonesApi.getMilestones(
                                mileStoneDto.getProjectId(),
                                mileStoneDto.getMilestoneState());
                    }
                } else {
                    if (mileStoneDto.getSearch() != null) {
                        return mileStonesApi.getMilestones(
                                mileStoneDto.getProjectId(),
                                mileStoneDto.getSearch());
                    }
                }
                return listMilestones(mileStoneDto.getProjectId(), null, null);
            } else {
                throw new FeignException("error.milestones.query");
            }
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Milestone queryMilestone(Integer projectId, Integer milestoneId) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getMileStonesApi()
                    .getMilestone(projectId, milestoneId);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);

        }
    }
}
