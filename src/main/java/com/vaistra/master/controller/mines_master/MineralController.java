package com.vaistra.master.controller.mines_master;


import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.mines_master.MineralDto;
import com.vaistra.master.dto.mines_master.VehicleDto;
import com.vaistra.master.service.mines_master.MineralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mineral")
public class MineralController {
    public final MineralService mineralService;

    @Autowired
    public MineralController(MineralService mineralService){

        this.mineralService = mineralService;
    }

    @PostMapping
    public ResponseEntity<String> addMineral(@RequestBody @Valid MineralDto mineralDto){
        return new ResponseEntity<>(mineralService.addMineral(mineralDto), HttpStatus.OK);
    }

    @PutMapping("/{mineralId}")
    public ResponseEntity<String> updateMineral(@PathVariable Integer mineralId, @RequestBody @Valid MineralDto mineralDto){
        return new ResponseEntity<>(mineralService.updateMineral(mineralId,mineralDto), HttpStatus.OK);
    }

    @DeleteMapping("/{mineralId}")
    public ResponseEntity<String> deleteMineral(@PathVariable Integer mineralId){
        return new ResponseEntity<>(mineralService.deleteMineral(mineralId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getMineral(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = "mineralId", required = false) String sortBy,
                                                   @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(mineralService.getMineral(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getMineralByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = "mineralId", required = false) String sortBy,
                                                            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(mineralService.getMineralByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }
}
