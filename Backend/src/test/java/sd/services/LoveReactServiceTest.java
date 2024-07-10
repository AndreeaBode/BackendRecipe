package sd.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sd.entities.LoveReact;
import sd.repositories.AddedRecipeRepository;
import sd.repositories.ExtractedRecipeRepository;
import sd.repositories.LoveReactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoveReactServiceTest {

    @Mock
    private LoveReactRepository loveReactRepository;

    @Mock
    private AddedRecipeRepository addedRecipeRepository;

    @Mock
    private ExtractedRecipeRepository extractedRecipeRepository;

    @InjectMocks
    private LoveReactService loveReactService;

    private LoveReact loveReact;

    @BeforeEach
    void setUp() {
        loveReact = new LoveReact();
        loveReact.setUserId(1);
        loveReact.setRecipeId(1001);
        loveReact.setName("test_recipe");
    }

    @Test
    void saveLoveReact_Success() {
        when(loveReactRepository.findByUserIdAndRecipeIdAndName(anyInt(), anyInt(), anyString()))
                .thenReturn(Optional.empty());
        when(loveReactRepository.save(any(LoveReact.class))).thenReturn(loveReact);

        LoveReact savedLoveReact = loveReactService.saveLoveReact(1, 1001, "test_recipe");

        assertNotNull(savedLoveReact);
        assertEquals("test_recipe", savedLoveReact.getName());
        verify(loveReactRepository, times(1)).findByUserIdAndRecipeIdAndName(anyInt(), anyInt(), anyString());
        verify(loveReactRepository, times(1)).save(any(LoveReact.class));
    }

    @Test
    void deleteLoveReact_Success() {
        when(loveReactRepository.findByUserIdAndRecipeIdAndName(anyInt(), anyInt(), anyString()))
                .thenReturn(Optional.of(loveReact));

        loveReactService.deleteLoveReact(1, 1001, "test_recipe");

        verify(loveReactRepository, times(1)).findByUserIdAndRecipeIdAndName(anyInt(), anyInt(), anyString());
        verify(loveReactRepository, times(1)).delete(any(LoveReact.class));
    }

    @Test
    void checkIfLiked_True() {
        List<LoveReact> loveReactList = new ArrayList<>();
        loveReactList.add(loveReact);

        when(loveReactRepository.findByUserIdAndRecipeId(anyInt(), anyInt())).thenReturn(loveReactList);

        boolean result = loveReactService.checkIfLiked(1, 1001);

        assertTrue(result);
        verify(loveReactRepository, times(1)).findByUserIdAndRecipeId(anyInt(), anyInt());
    }

    @Test
    void checkIfLiked_False() {
        when(loveReactRepository.findByUserIdAndRecipeId(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        boolean result = loveReactService.checkIfLiked(1, 1001);

        assertFalse(result);
        verify(loveReactRepository, times(1)).findByUserIdAndRecipeId(anyInt(), anyInt());
    }

}

