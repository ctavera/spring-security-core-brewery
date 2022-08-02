package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

//    @Test
//    void deleteBeerUrlParams() throws Exception {
//        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
//        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144")
//                        .param("apiKey", "spring")
//                        .param("apiSecret", "kahlua"))
//                .andExpect(status().isOk());
//    }

//    @Test
//    void deleteBeerBadCredsUrlParams() throws Exception {
//        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
//        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144")
//                        .param("apiKey", "spring")
//                        .param("apiSecret", "kahluaXXXX"))
//                .andExpect(status().isUnauthorized());
//    }

//    @Test
//    void deleteBeerBadCreds() throws Exception {
//        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
//        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144")
//                        .header("Api-Key", "spring")
//                        .header("Api-Secret", "kahluaXXXX"))
//                .andExpect(status().isUnauthorized());
//    }

//    @Test
//    void deleteBeer() throws Exception {
//        //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
//        mockMvc.perform(delete("/api/v1/beer/cfca9075-1cc5-4532-84ea-739544af7144")
//                .header("Api-Key", "spring")
//                .header("Api-Secret", "kahlua"))
//                .andExpect(status().isOk());
//    }

    @DisplayName("Delete Tests")
    @Nested
    class DeleteTests{

        public Beer beerToDelete(){
            Random random = new Random();

            return beerRepository.saveAndFlush(Beer.builder()
                    .beerName("Delete Me Beer")
                    .beerStyle(BeerStyleEnum.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(String.valueOf(random.nextInt(99999999)))
                    .build());
        }
        @Test
        void deleteBeerHttpBasic() throws Exception {
            //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .with(httpBasic("spring", "kahlua")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void deleteBeerHttpBasicUserRole() throws Exception {
            //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .with(httpBasic("user", "password")))
                    .andExpect(status().isForbidden()); //not allowed to the resource
        }

        @Test
        void deleteBeerHttpBasicCustomerRole() throws Exception {
            //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                            .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerNoAuth() throws Exception {
            //Authentication Filter Use Case: legacy aplication who send headers with Api Key and Secret, not recommended
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/beer/" + beer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerFormADMIN() throws Exception {
        mockMvc.perform(get("/beers").param("beerName", "")
                        .with(httpBasic("spring", "kahlua")))
                .andExpect(status().isOk());
    }
}
