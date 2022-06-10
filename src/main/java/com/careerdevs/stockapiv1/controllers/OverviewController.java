package com.careerdevs.stockapiv1.controllers;

import com.careerdevs.stockapiv1.models.Overview;
import com.careerdevs.stockapiv1.repositories.OverviewRepository;
import com.careerdevs.stockapiv1.utils.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepository overviewRepository;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";


    @GetMapping("/test")

    public ResponseEntity<?> testOverView(RestTemplate restTemplate) {
        try {
            String url = BASE_URL + "&symbol=IBM&apikey=demo";

            Overview responseKey = restTemplate.getForObject(url, Overview.class);
            if(responseKey == null){
                return  ApiError.customApiError("No data retrieved",500);
            }else if (responseKey.getSymbol() == null){
                return ApiError.customApiError("No data retreved", 404);
            }

            return ResponseEntity.ok(responseKey);
        } catch (IllegalArgumentException e) {
            return ApiError.customApiError("Error in testOverview.URI is not absolute.Check URL", 500);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }


    }

    @GetMapping("/{symbol}")

    public ResponseEntity<?> getOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {
        try {

            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;
            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);


            System.out.println(url);


            //checking to see if the data is null
            if (alphaVantageResponse == null) {
                return ApiError.customApiError("Did not recieve response from AV.",404);
            } else if (alphaVantageResponse.equals("{}")) {
                return ApiError.customApiError("Invalid Stock Symbol" + symbol, 500);
            }


            return ResponseEntity.ok(alphaVantageResponse);
        } catch (IllegalArgumentException e) {
            return ApiError.customApiError("Error in testOverview.URI is not absolute.Check URL", 500);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @PostMapping("/{symbol}")
    public ResponseEntity<?> testUploadOverview(RestTemplate restTemplate,@PathVariable String symbol) {
        try {
            String apiKey = env.getProperty("AV_API_KEY");

            String url = BASE_URL + "&symbol="+ symbol +"&apikey="+apiKey;
            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            // this is going to be a saved overview
            assert alphaVantageResponse != null;
            Overview savedOverview = overviewRepository.save(alphaVantageResponse);


            System.out.println(url);
            //checking to see if the data is null
            if (alphaVantageResponse == null) {
                return ApiError.customApiError("Did not recieve response from AV.", 404);
            } else if (alphaVantageResponse.equals("{}")) {
                return ApiError.customApiError("Invalid Stock Symbol" ,500);
            }


            return ResponseEntity.ok(savedOverview);
        }
            catch(DataIntegrityViolationException e ){
                return ApiError.customApiError("Can Not Create duplicate stocks", 400);
            }
         catch (IllegalArgumentException e) {
            return ApiError.customApiError("Error in testOverview.URI is not absolute.Check URL", 500);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }


    // Get All Overview from SQL database.
    //return [] of all Overview
    @GetMapping("/all")
    private ResponseEntity<?> getAllOverView(){

        try{
            Iterable<Overview> allOverview = overviewRepository.findAll();
            return  ResponseEntity.ok(allOverview);

        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }
    }

  // GET one Overview by ID from SQL database  return found overview OR 404 if not found
    @GetMapping("/id/{id}")
    private ResponseEntity<?> findById(@PathVariable String id ){
        try{
            Optional<Overview> foundOverview = overviewRepository.findById(Long.parseLong(id));
           if(foundOverview.isEmpty()){
               return ApiError.customApiError("Id: did not match any overview entered", 404);
           }
             return ResponseEntity.ok(foundOverview);

        }catch(NumberFormatException e){
            return  ApiError.customApiError("Invalid Id: Must be a Number"+ id , 400);


        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }
    }



    // delete all OVERview from SQL database
    //return # of delete overview
    @DeleteMapping("/all")
    private ResponseEntity<?> deleteAllFromOverview(){
        try{
            long allOverviewCount = overviewRepository.count();
            if(allOverviewCount == 0) return  ResponseEntity.ok("No overviews to Delete");
            overviewRepository.deleteAll();

            return ResponseEntity.ok("Deleted Overviews " +allOverviewCount);

        }catch(NumberFormatException e){
            return  ApiError.customApiError("Invalid Id: Must be a Number", 400);
        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }
    }


    @DeleteMapping("/id/{id}")
    private ResponseEntity<?> deleteId(@PathVariable String id){
        try{
            long overviewId = Long.parseLong(id);
            Optional<Overview> foundOverview = overviewRepository.findById(overviewId);
            if(foundOverview.isEmpty()){
                return ApiError.customApiError(id +" did not match any overview ", 404);
            }
            overviewRepository.deleteById(overviewId);

            return ResponseEntity.ok("Deleted overview "+foundOverview);

        }catch(NumberFormatException e){
            return  ApiError.customApiError("Invalid Id: Must be a Number", 400);


        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }

    }


    //@GetMapping("/symbol/{symbol}")
//    private ResponseEntity<?> getOverviewBySymbol(@PathVariable String symbol){
//      try{
//
//          //Overview foundOverview = overviewRepository.findBySymbol(symbol);
//       // if(foundOverview== null){
//            ApiErrorHandling.customApiError("This was found to be Null" + symbol, HttpStatus.NOT_FOUND);
//
//        }
//        return new ResponseEntity<>(foundOverview,HttpStatus.OK);
//    }catch(HttpClientErrorException e){
//         return ApiErrorHandling.customApiError(e.getMessage(),e.getStatusCode());
//      }catch(Exception e){
//        return   ApiErrorHandling.genericApiError(e);
//      }
//
//}
}