package com.live.mosque.controller;

import com.live.mosque.data.Mosque;
import com.live.mosque.service.MosqueService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("mosque")
public class MosqueController {

    @Resource
    private MosqueService mosqueService;

    @GetMapping
    public List<Mosque> getAll() {
        return mosqueService.getAllMosque();
    }

    @GetMapping("/{uid}")
    public Mosque get(@PathVariable String uid) {
        return mosqueService.findByKey(uid);
    }

    @PostMapping
    public void add(@RequestBody Mosque mosque) {
        mosqueService.addMosque(mosque);
    }

    @PatchMapping
    public Mosque patch(@RequestBody Mosque mosque) {
        return mosqueService.patchUpdate(mosque);
    }

    @PutMapping
    public Mosque put(@RequestBody Mosque mosque) {
        return mosqueService.putUpdate(mosque);
    }

    @DeleteMapping
    public void delete(String uid) {
        mosqueService.delete(uid);
    }
}
