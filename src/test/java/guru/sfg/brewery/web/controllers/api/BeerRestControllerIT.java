package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

//    @Test
//    void deleteBeerUrlParams() throws Exception {
//        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
//        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144")
//                        .param("apiKey", "spring")
//                        .param("apiSecret", "kahlua"))
//                .andExpect(status().isOk());
//    }

    @Test
    void deleteBeerBadCredsUrlParams() throws Exception {
        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144")
                        .param("apiKey", "spring")
                        .param("apiSecret", "kahluaXXXX"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerBadCreds() throws Exception {
        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "kahluaXXXX"))
                .andExpect(status().isUnauthorized());
    }

//    @Test
//    void deleteBeer() throws Exception {
//        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
//        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144")
//                .header("Api-Key", "spring")
//                .header("Api-Secret", "kahlua"))
//                .andExpect(status().isOk());
//    }

    @Test
    void deleteBeerHttpBasic() throws Exception {
        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144")
                        .with(httpBasic("spring", "kahlua")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {
        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());
    }
}
