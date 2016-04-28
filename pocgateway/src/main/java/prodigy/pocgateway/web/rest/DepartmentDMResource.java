package prodigy.pocgateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import prodigy.pocgateway.domain.DepartmentDM;
import prodigy.pocgateway.repository.DepartmentDMRepository;
import prodigy.pocgateway.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DepartmentDM.
 */
@RestController
@RequestMapping("/api")
public class DepartmentDMResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentDMResource.class);
        
    @Inject
    private DepartmentDMRepository departmentDMRepository;
    
    /**
     * POST  /department-dms : Create a new departmentDM.
     *
     * @param departmentDM the departmentDM to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departmentDM, or with status 400 (Bad Request) if the departmentDM has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/department-dms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DepartmentDM> createDepartmentDM(@RequestBody DepartmentDM departmentDM) throws URISyntaxException {
        log.debug("REST request to save DepartmentDM : {}", departmentDM);
        if (departmentDM.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("departmentDM", "idexists", "A new departmentDM cannot already have an ID")).body(null);
        }
        DepartmentDM result = departmentDMRepository.save(departmentDM);
        return ResponseEntity.created(new URI("/api/department-dms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("departmentDM", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /department-dms : Updates an existing departmentDM.
     *
     * @param departmentDM the departmentDM to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departmentDM,
     * or with status 400 (Bad Request) if the departmentDM is not valid,
     * or with status 500 (Internal Server Error) if the departmentDM couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/department-dms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DepartmentDM> updateDepartmentDM(@RequestBody DepartmentDM departmentDM) throws URISyntaxException {
        log.debug("REST request to update DepartmentDM : {}", departmentDM);
        if (departmentDM.getId() == null) {
            return createDepartmentDM(departmentDM);
        }
        DepartmentDM result = departmentDMRepository.save(departmentDM);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("departmentDM", departmentDM.getId().toString()))
            .body(result);
    }

    /**
     * GET  /department-dms : get all the departmentDMS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of departmentDMS in body
     */
    @RequestMapping(value = "/department-dms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DepartmentDM> getAllDepartmentDMS() {
        log.debug("REST request to get all DepartmentDMS");
        List<DepartmentDM> departmentDMS = departmentDMRepository.findAll();
        return departmentDMS;
    }

    /**
     * GET  /department-dms/:id : get the "id" departmentDM.
     *
     * @param id the id of the departmentDM to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departmentDM, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/department-dms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DepartmentDM> getDepartmentDM(@PathVariable Long id) {
        log.debug("REST request to get DepartmentDM : {}", id);
        DepartmentDM departmentDM = departmentDMRepository.findOne(id);
        return Optional.ofNullable(departmentDM)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /department-dms/:id : delete the "id" departmentDM.
     *
     * @param id the id of the departmentDM to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/department-dms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDepartmentDM(@PathVariable Long id) {
        log.debug("REST request to delete DepartmentDM : {}", id);
        departmentDMRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("departmentDM", id.toString())).build();
    }

}
