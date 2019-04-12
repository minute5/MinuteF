package per.zongwlee.gitlab.app.service;

import java.util.List;

import per.zongwlee.gitlab.api.dto.CommitDTO;
import org.gitlab4j.api.models.Commit;

import per.zongwlee.gitlab.api.dto.CommitStatuseDTO;

public interface CommitService {
    CommitDTO getCommit(Integer projectId, String sha, Integer userId);

    List<CommitStatuseDTO> getCommitStatuse(Integer projectId, String sha, Integer userId);

    List<Commit> getCommits(Integer gitLabProjectId, String ref, String since);

    List<Commit> listCommits(Integer gilabProjectId , int page, int size, Integer userId);
}
