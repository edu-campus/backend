package de.hems.backend.endpoints;

import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

@RestController
@RequestMapping("/api")
public class HealthEndpoint {

    @GetMapping("/health")
    public ResponseEntity cpu() {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cpu-load", processor.getSystemCpuLoad(10));
        jsonObject.addProperty("memory-used", memory.getTotal()-memory.getAvailable());
        jsonObject.addProperty("memory-total", memory.getTotal());
        jsonObject.addProperty("memory-free", memory.getAvailable());
        jsonObject.addProperty("name", processor.getProcessorIdentifier().getName());
        jsonObject.addProperty("cpu-cores", processor.getLogicalProcessorCount());
        return ResponseEntity.ok(jsonObject.toString());
    }
}
