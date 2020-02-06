package jm.controller.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jm.PluginService;
import jm.dto.ZoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/rest/plugin")
@Tag(name = "plugin", description = "Plugin API")
public class PluginRestController {

  private PluginService<ZoomDTO> zoomPlugin;

  @Autowired
  public void setPluginService(PluginService<ZoomDTO> pluginService) {
    this.zoomPlugin = pluginService;
  }

  @GetMapping("/zoom")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "OK: authorized in zoom"),
          @ApiResponse(responseCode = "400", description = "BAD_REQUEST: unable to authorize in zoom")
  })
  public ResponseEntity<ZoomDTO> zoomOAuth(Principal principal) {
    if (principal != null) {
      return ResponseEntity.ok(zoomPlugin.create(principal.getName()));
    }
    return ResponseEntity.badRequest().build();
  }
}
