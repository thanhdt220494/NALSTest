package com.nals.test.web.rest;

import com.nals.test.NalsTestApp;
import com.nals.test.domain.Work;
import com.nals.test.repository.WorkRepository;
import com.nals.test.service.WorkService;
import com.nals.test.service.dto.WorkDTO;
import com.nals.test.service.mapper.WorkMapper;
import com.nals.test.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.nals.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nals.test.domain.enumeration.Status;
/**
 * Integration tests for the {@link WorkResource} REST controller.
 */
@SpringBootTest(classes = NalsTestApp.class)
public class WorkResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.PLANNING;
    private static final Status UPDATED_STATUS = Status.DOING;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private WorkService workService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restWorkMockMvc;

    private Work work;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkResource workResource = new WorkResource(workService);
        this.restWorkMockMvc = MockMvcBuilders.standaloneSetup(workResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Work createEntity(EntityManager em) {
        Work work = new Work()
            .name(DEFAULT_NAME)
            .startingDate(DEFAULT_STARTING_DATE)
            .endingDate(DEFAULT_ENDING_DATE)
            .status(DEFAULT_STATUS);
        return work;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Work createUpdatedEntity(EntityManager em) {
        Work work = new Work()
            .name(UPDATED_NAME)
            .startingDate(UPDATED_STARTING_DATE)
            .endingDate(UPDATED_ENDING_DATE)
            .status(UPDATED_STATUS);
        return work;
    }

    @BeforeEach
    public void initTest() {
        work = createEntity(em);
    }

    @Test
    @Transactional
    public void createWork() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work
        WorkDTO workDTO = workMapper.toDto(work);
        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isCreated());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate + 1);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWork.getStartingDate()).isEqualTo(DEFAULT_STARTING_DATE);
        assertThat(testWork.getEndingDate()).isEqualTo(DEFAULT_ENDING_DATE);
        assertThat(testWork.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work with an existing ID
        work.setId(1L);
        WorkDTO workDTO = workMapper.toDto(work);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setName(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setStartingDate(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setEndingDate(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = workRepository.findAll().size();
        // set the field null
        work.setStatus(null);

        // Create the Work, which fails.
        WorkDTO workDTO = workMapper.toDto(work);

        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorks() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get all the workList
        restWorkMockMvc.perform(get("/api/works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(work.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startingDate").value(hasItem(DEFAULT_STARTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endingDate").value(hasItem(DEFAULT_ENDING_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", work.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(work.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startingDate").value(DEFAULT_STARTING_DATE.toString()))
            .andExpect(jsonPath("$.endingDate").value(DEFAULT_ENDING_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWork() throws Exception {
        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Update the work
        Work updatedWork = workRepository.findById(work.getId()).get();
        // Disconnect from session so that the updates on updatedWork are not directly saved in db
        em.detach(updatedWork);
        updatedWork
            .name(UPDATED_NAME)
            .startingDate(UPDATED_STARTING_DATE)
            .endingDate(UPDATED_ENDING_DATE)
            .status(UPDATED_STATUS);
        WorkDTO workDTO = workMapper.toDto(updatedWork);

        restWorkMockMvc.perform(put("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isOk());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWork.getStartingDate()).isEqualTo(UPDATED_STARTING_DATE);
        assertThat(testWork.getEndingDate()).isEqualTo(UPDATED_ENDING_DATE);
        assertThat(testWork.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingWork() throws Exception {
        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Create the Work
        WorkDTO workDTO = workMapper.toDto(work);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkMockMvc.perform(put("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWork() throws Exception {
        // Initialize the database
        workRepository.saveAndFlush(work);

        int databaseSizeBeforeDelete = workRepository.findAll().size();

        // Delete the work
        restWorkMockMvc.perform(delete("/api/works/{id}", work.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
