package jm.controller;

import jm.AnalyticService;
import jm.model.Channel;
import jm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api/workspace/analytic")
public class AnalyticRestController {

    private AnalyticService analyticService;

    @Autowired
    public void setAnalyticService(AnalyticService analyticService) {
        this.analyticService = analyticService;
    }

    @GetMapping("/{id}/messages-count")
    public ResponseEntity<Integer> getMessagesCountForWorkspace(@PathVariable Long id) {
        return ResponseEntity.ok(analyticService.getMessagesCountForWorkspace(id));
    }

    @GetMapping("/{id}/channels/{period}")
    public ResponseEntity<List<Channel>> getAllChannelsByWorkspaceId(
            @PathVariable Long id,
            @PathVariable("period") Boolean lastMonth) {
        return new ResponseEntity<>(analyticService.getAllChannelsForWorkspace(id, lastMonth), HttpStatus.OK);
    }

    @GetMapping("/{id}/users/{lastMonth}")
    public ResponseEntity<List<User>> getAllUsersByWorkspaceId(
            @PathVariable Long id,
            @PathVariable Boolean lastMonth) {
        return new ResponseEntity<>(
                lastMonth
                        ? analyticService.getUsersForWorkspaceByIdForLastMonth(id)
                        : analyticService.getUsersForWorkspaceById(id),
                HttpStatus.OK
        );
    }
}