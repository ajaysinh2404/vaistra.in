package com.vaistra.master.controller.mines_master;


import com.vaistra.master.dto.HttpResponse;
import com.vaistra.master.dto.cscv_master.VillageDto;
import com.vaistra.master.dto.mines_master.VehicleDto;
import com.vaistra.master.dto.mines_master.VehicleDto_Update;
import com.vaistra.master.service.mines_master.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService){

        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<String> addVehicle(@RequestBody @Valid VehicleDto vehicleDto){
        return new ResponseEntity<>(vehicleService.addVehicle(vehicleDto), HttpStatus.OK);
    }

    @PatchMapping("/{vehicleId}")
    public ResponseEntity<String> updateVehicle(@PathVariable Integer vehicleId, @RequestBody @Valid VehicleDto_Update vehicleDto){
        return new ResponseEntity<>(vehicleService.updateVehicle(vehicleId,vehicleDto), HttpStatus.OK);
    }


    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Integer vehicleId){
        return new ResponseEntity<>(vehicleService.deleteVehicle(vehicleId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getVehicle(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = "vehicleId", required = false) String sortBy,
                                                   @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection){
        return new ResponseEntity<>(vehicleService.getVehicle(pageNo,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> getVehicleByKeyword(@RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = "vehicleId", required = false) String sortBy,
                                                            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                                                            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword){
        return new ResponseEntity<>(vehicleService.getVehicleByKeyword(pageNo,pageSize,sortBy,sortDirection,keyword),HttpStatus.OK);
    }


}
