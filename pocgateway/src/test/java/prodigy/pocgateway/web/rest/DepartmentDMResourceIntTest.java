package prodigy.pocgateway.web.rest;

import prodigy.pocgateway.PocgatewayApp;
import prodigy.pocgateway.domain.DepartmentDM;
import prodigy.pocgateway.repository.DepartmentDMRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DepartmentDMResource REST controller.
 *
 * @see DepartmentDMResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PocgatewayApp.class)
@WebAppConfiguration
@IntegrationTest
public class DepartmentDMResourceIntTest {


    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final String DEFAULT_DESIGNATION = "AAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private DepartmentDMRepository departmentDMRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDepartmentDMMockMvc;

    private DepartmentDM departmentDM;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DepartmentDMResource departmentDMResource = new DepartmentDMResource();
        ReflectionTestUtils.setField(departmentDMResource, "departmentDMRepository", departmentDMRepository);
        this.restDepartmentDMMockMvc = MockMvcBuilders.standaloneSetup(departmentDMResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        departmentDM = new DepartmentDM();
        departmentDM.setVersion(DEFAULT_VERSION);
        departmentDM.setDesignation(DEFAULT_DESIGNATION);
        departmentDM.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDepartmentDM() throws Exception {
        int databaseSizeBeforeCreate = departmentDMRepository.findAll().size();

        // Create the DepartmentDM

        restDepartmentDMMockMvc.perform(post("/api/department-dms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(departmentDM)))
                .andExpect(status().isCreated());

        // Validate the DepartmentDM in the database
        List<DepartmentDM> departmentDMS = departmentDMRepository.findAll();
        assertThat(departmentDMS).hasSize(databaseSizeBeforeCreate + 1);
        DepartmentDM testDepartmentDM = departmentDMS.get(departmentDMS.size() - 1);
        assertThat(testDepartmentDM.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testDepartmentDM.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testDepartmentDM.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDepartmentDMS() throws Exception {
        // Initialize the database
        departmentDMRepository.saveAndFlush(departmentDM);

        // Get all the departmentDMS
        restDepartmentDMMockMvc.perform(get("/api/department-dms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(departmentDM.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
                .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getDepartmentDM() throws Exception {
        // Initialize the database
        departmentDMRepository.saveAndFlush(departmentDM);

        // Get the departmentDM
        restDepartmentDMMockMvc.perform(get("/api/department-dms/{id}", departmentDM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(departmentDM.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartmentDM() throws Exception {
        // Get the departmentDM
        restDepartmentDMMockMvc.perform(get("/api/department-dms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartmentDM() throws Exception {
        // Initialize the database
        departmentDMRepository.saveAndFlush(departmentDM);
        int databaseSizeBeforeUpdate = departmentDMRepository.findAll().size();

        // Update the departmentDM
        DepartmentDM updatedDepartmentDM = new DepartmentDM();
        updatedDepartmentDM.setId(departmentDM.getId());
        updatedDepartmentDM.setVersion(UPDATED_VERSION);
        updatedDepartmentDM.setDesignation(UPDATED_DESIGNATION);
        updatedDepartmentDM.setDescription(UPDATED_DESCRIPTION);

        restDepartmentDMMockMvc.perform(put("/api/department-dms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDepartmentDM)))
                .andExpect(status().isOk());

        // Validate the DepartmentDM in the database
        List<DepartmentDM> departmentDMS = departmentDMRepository.findAll();
        assertThat(departmentDMS).hasSize(databaseSizeBeforeUpdate);
        DepartmentDM testDepartmentDM = departmentDMS.get(departmentDMS.size() - 1);
        assertThat(testDepartmentDM.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testDepartmentDM.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testDepartmentDM.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteDepartmentDM() throws Exception {
        // Initialize the database
        departmentDMRepository.saveAndFlush(departmentDM);
        int databaseSizeBeforeDelete = departmentDMRepository.findAll().size();

        // Get the departmentDM
        restDepartmentDMMockMvc.perform(delete("/api/department-dms/{id}", departmentDM.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DepartmentDM> departmentDMS = departmentDMRepository.findAll();
        assertThat(departmentDMS).hasSize(databaseSizeBeforeDelete - 1);
    }
}
