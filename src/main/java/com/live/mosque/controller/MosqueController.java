package com.live.mosque.controller;

import com.live.mosque.data.Mosque;
import com.live.mosque.service.MosqueService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mosque")
public class MosqueController {

    private static final Logger log = LoggerFactory.getLogger(MosqueController.class);
    @Resource
    private MosqueService mosqueService;

    @GetMapping("/all")
    public List<Mosque> getAll(@RequestParam(defaultValue = "100") int count) {
        return mosqueService.getAllMosque(count);
    }

    @GetMapping("/{uid}")
    public Mosque get(@PathVariable String uid) {
        return mosqueService.findByKey(uid);
    }

    @PostMapping
    public void add(@RequestBody Mosque mosque) {
        try {
            if (mosqueService.findByKey(mosque.getUid()) != null) {
                throw new RuntimeException("item found");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        mosqueService.addMosque(mosque);
    }

    @PatchMapping
    public Mosque patch(@RequestBody Mosque mosque) {
        try {
            if (mosqueService.findByKey(mosque.getUid()) != null) {
                throw new RuntimeException("item found");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return mosqueService.patchUpdate(mosque);
    }

    @PutMapping
    public Mosque put(@RequestBody Mosque mosque) {
        try {
            if (mosqueService.findByKey(mosque.getUid()) != null) {
                throw new RuntimeException("item found");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return mosqueService.putUpdate(mosque);
    }

    @DeleteMapping("/{uid}")
    public void delete(@PathVariable String uid) {
        mosqueService.delete(uid);
    }
}
