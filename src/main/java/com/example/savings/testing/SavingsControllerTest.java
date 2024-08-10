package com.example.savings.testing;

import com.example.savings.entities.Savingstable;
import com.example.savings.repositories.SavingsRepository;
import com.example.savings.web.SavingsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SavingsControllerTest {

    @Mock
    private SavingsRepository savingsRepository;

    @InjectMocks
    private SavingsController savingsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(savingsController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void testSaveEntryNew() throws Exception {
        Savingstable savingstable = new Savingstable();
        savingstable.setCustNo("12345");
        savingstable.setCustName("John Doe");

        when(savingsRepository.findByCustNo(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/saveEntry")
                        .param("custNo", "12345")
                        .param("custName", "John Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(savingsRepository, times(1)).save(any(Savingstable.class));
    }

    @Test
    public void testSaveEntryExisting() throws Exception {
        Savingstable savingstable = new Savingstable();
        savingstable.setCustNo("12345");

        when(savingsRepository.findByCustNo(any())).thenReturn(Arrays.asList(savingstable));

        mockMvc.perform(post("/saveEntry")
                        .param("custNo", "12345"))
                .andExpect(status().isOk())
                .andExpect(view().name("newEntry"));
    }

    @Test
    public void testEditEntry() throws Exception {
        Savingstable savingstable = new Savingstable();
        savingstable.setId(1L);

        when(savingsRepository.findById(anyLong())).thenReturn(Optional.of(savingstable));

        mockMvc.perform(get("/editentry")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editEntry"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(savingsRepository, times(1)).deleteById(anyLong());
    }
}
