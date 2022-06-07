package com.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class CityController {

//    @Autowired
//    private CityService cityService;
//
//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    public ResponseEntity<?> save(@RequestBody City city) {
//        cityService.save(city);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public ResponseEntity<List<City>> getAll() throws SQLException {
//        List<City> cities = cityService.getAll();
//        return new ResponseEntity<>(cities, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity<City> get(@PathVariable(value = "id") Long id) {
//        City city = cityService.get(id).isPresent() ? cityService.get(id).get() : null;
//        return new ResponseEntity<>(city, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
//    public ResponseEntity<City> get(@PathVariable(value = "name") String name) {
//        City city = cityService.getByName(name);
//        return new ResponseEntity<>(city, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
//    public ResponseEntity<City> delete(@PathVariable(value = "name") String name) {
//        cityService.delete(name);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
