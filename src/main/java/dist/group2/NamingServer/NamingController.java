package dist.group2.NamingServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "api/naming")
public class NamingController {
    private final NamingService service;

    @Autowired
    public NamingController(NamingService service) {
        this.service = service;
    }

    @PostMapping("/nodes")
    public ResponseEntity<Void> addNode(@RequestBody Map<String, String> node) {
        this.service.addNode(node);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/nodes/{nodeName}")
    public ResponseEntity<Void> deleteNode(@PathVariable("nodeName") String nodeName) {
        this.service.deleteNode(nodeName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/files/{fileName}")
    public ResponseEntity<String> findFile(@PathVariable("fileName") String fileName) {
        String result = this.service.findFile(fileName);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/nodes/{nodeID}/ip")
    public ResponseEntity<String> getIPAddress(@PathVariable("nodeID") int nodeID) {
        String ipAddress = this.service.getIPAddress(nodeID);
        return ResponseEntity.ok(ipAddress);
    }
}
