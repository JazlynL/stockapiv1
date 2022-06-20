package com.careerdevs.stockapiv1.controllers;

import com.careerdevs.stockapiv1.models.Overview;
import com.careerdevs.stockapiv1.repositories.OverviewRepository;
import com.careerdevs.stockapiv1.utils.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepository overviewRepository;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";





    // Get All Overview from SQL database.
    //return [] of all Overview
    @GetMapping("/all")
    private ResponseEntity<?> getAllOverView(){

        try{
            Iterable<Overview> allOverview = overviewRepository.findAll();
            return  ResponseEntity.ok(allOverview);

        }catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());}
        catch(DataIntegrityViolationException e ){
            return ApiError.customApiError("Can Not Create duplicate stocks", 400);
        }

        catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    // GET one Overview by ID from SQL database  return found overview OR 404 if not found
    @GetMapping("/id/{id}")
    private ResponseEntity<?> findById(@PathVariable String id ){
        try{
            long idFound = Long.parseLong(id);
            List <Overview> foundOverview = overviewRepository.findById(idFound);
            if(foundOverview.isEmpty()){
                ApiError.throwErr(404, id +"Id entered was not found.");
            }
            return ResponseEntity.ok(foundOverview);

        }
        catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());}
        catch(NumberFormatException e){
            return  ApiError.customApiError("Invalid Id: Must be a Number"+ id , 400);


        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }
    }
    // create a find and delete route for symbol and sector.
    @GetMapping("/{field}/{value}")
    private ResponseEntity<?> getOverviewByField(@PathVariable String field, @PathVariable String value){
        try{
            List<Overview> foundOverview = null;
            field = field.toLowerCase();
            // condesing the code to a switch statement.so we are able to look it up by  key value.
            switch(field){
                case "id"-> foundOverview = overviewRepository.findById(Long.parseLong(value));
                case "name " -> foundOverview = overviewRepository.findByName(value);
                case "symbol"-> foundOverview = overviewRepository.findBySymbol(value);
                case "exchange" -> foundOverview = overviewRepository.findByExchange(value);
                case "assettype"-> foundOverview = overviewRepository.findByAssetType(value);
                case "sector"-> foundOverview = overviewRepository.findBySector(value);
                case "currency" -> foundOverview = overviewRepository.findByCurrency(value);
                case "country" -> foundOverview = overviewRepository.findByCountry(value);
                case "marketcapgte" -> foundOverview = overviewRepository.findByMarketCapGreaterThanEqual(Long.parseLong(value));
                case "marketcaplte" -> foundOverview = overviewRepository.findByMarketCapLessThanEqual(Long.parseLong(value));
                case "yearhighgte" -> foundOverview = overviewRepository.findByYearHighGreaterThanEqual(Float.parseFloat(value));
                case "yearhighlte" -> foundOverview = overviewRepository.findByYearHighLessThanEqual(Float.parseFloat(value));
                case "yearlowgte" -> foundOverview = overviewRepository.findByYearLowGreaterThanEqual(Float.parseFloat(value));
                case "yearlowlte" -> foundOverview = overviewRepository.findByYearLowLessThanEqual(Float.parseFloat(value));
                case "ddafter" -> foundOverview = overviewRepository.findByDividendDateGreaterThanEqual(value);
                case "ddbefore" -> foundOverview = overviewRepository.findByDividendDateLessThanEqual(value);
            }

          //  if(overviewRepository.findByDividendDateGreaterThanEqual(value) )

            if(foundOverview == null || foundOverview.isEmpty()){
                ApiError.throwErr(404, field+" did not match any field entered.");
            }
            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());}
        catch(NumberFormatException e){
            return  ApiError.customApiError("Invalid Id: Must be a Number"+field , 400);


        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }

    }

    // get mapping for industry


    @DeleteMapping("/{field}/{value}")
    private ResponseEntity<?> deleteOverviewByField(@PathVariable String field, @PathVariable String value){
        try{
            List<Overview> foundOverview = null;
            field = field.toLowerCase();
            switch(field){
                case "id"-> foundOverview = overviewRepository.deleteById(Long.parseLong(value));
                case "name " -> foundOverview = overviewRepository.deleteByName(value);
                case "symbol"-> foundOverview = overviewRepository.deleteBySymbol(value);
                case "assettype"-> foundOverview = overviewRepository.deleteByAssetType(value);
                case "sector"-> foundOverview = overviewRepository.deleteBySector(value);
                case "currency" -> foundOverview = overviewRepository.deleteByCurrency(value);
                case "country" -> foundOverview = overviewRepository.deleteByCountry(value);
                case "exchange" -> foundOverview = overviewRepository.deleteByExchange(value);
                case "marketcapgte" -> foundOverview = overviewRepository.deleteByMarketCapGreaterThanEqual(Long.parseLong(value));
                case "marketcaplte" -> foundOverview = overviewRepository.deleteByMarketCapLessThanEqual(Long.parseLong(value));
                case "yearhighgte" -> foundOverview = overviewRepository.deleteByYearHighGreaterThanEqual(Float.parseFloat(value));
                case "yearhighlte" -> foundOverview = overviewRepository.deleteByYearHighLessThanEqual(Float.parseFloat(value));
                case "yearlowgte" -> foundOverview = overviewRepository.deleteByYearLowGreaterThanEqual(Float.parseFloat(value));
                case "yearlowlte" -> foundOverview = overviewRepository.deleteByYearLowLessThanEqual(Float.parseFloat(value));
            }

            if (foundOverview== null|| foundOverview.isEmpty()){
                ApiError.throwErr(404,field+"did not match any overview ");
            }
            return ResponseEntity.ok(foundOverview);
        } catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());}
        catch(NumberFormatException e){
            return  ApiError.customApiError("Invalid Id: Must be a Number"+ field , 400);


        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }

    }

    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<?> getOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {
        try {

            List<Overview> foundOverview = overviewRepository.findBySymbol(symbol);
            if(foundOverview.isEmpty()){
                ApiError.throwErr(404, symbol+ " did not match any overview");
            }
            return ResponseEntity.ok(foundOverview);
            }catch (HttpClientErrorException e) {
                return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
            }// we will always set this exception last because its the most generic exception that can be found
             catch (Exception e) {
                return ApiError.genericApiError(e);
            }
    }

    @PostMapping("/{symbol}")

     public ResponseEntity<?> uploadOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol){
        try{

            String apiKey = env.getProperty("AV_API_KEY");
          // we are  going to set the url
           String url = BASE_URL + "&symbol= " + symbol + "&apikey=" + apiKey;
         // this is the response
            Overview response = restTemplate.getForObject(url , Overview.class);

            if(response == null){
                ApiError.throwErr(500,"No response from the AV api");
            }else if (response.getSymbol() == null){
                ApiError.throwErr(404,symbol + " no symbol found that you have entered");
            }
            Overview savedOverview = overviewRepository.save(response);

            return ResponseEntity.ok(savedOverview);



        } catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());}
        catch (DataIntegrityViolationException e){
            return  ApiError.customApiError("Can not duplicate stock input.",400);
        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }
    }

    @PostMapping("/testupload")
    public ResponseEntity<?> testUpload(RestTemplate restTemplate){
        try{
            String apiKey = env.getProperty("AV_API_KEY");
            String [] testUploads = {"IBM","GOOG","AAPL","TM","GS"};
            ArrayList <Overview> overviews = new ArrayList<>();
            for(int i = 0 ; i < testUploads.length;i++){
                String symbol = testUploads[i];

                String url = BASE_URL +"&symbol=" + symbol + "&apikey=" + apiKey ;
                Overview alphaResponse = restTemplate.getForObject(url,Overview.class);
                if(alphaResponse == null){
                    ApiError.throwErr(500, " No response from AV");
                }else if(alphaResponse.getSymbol() == "{}"){
                    ApiError.throwErr(404, symbol+ " the symbol entered is invalid");
                }

                overviews.add(alphaResponse);
            }

            Iterable<Overview> savedOverview = overviewRepository.saveAll(overviews);


            return  ResponseEntity.ok(savedOverview);

        }catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());}
        catch (DataIntegrityViolationException e){
            return  ApiError.customApiError("Can not duplicate stock input.",400);
        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }
    }

//
//    @DeleteMapping("/all")
//    public ResponseEntity<?> deleteAllOverView(){
//      long foundItems = overviewRepository.count();
//      if(foundItems == 0){
//          return ResponseEntity.ok("no items have been found");
//      }
//      overviewRepository.deleteAll();
//
//      return  ResponseEntity.ok("these are deleted items entries "+foundItems);
//
//    }
//    @GetMapping("/asset/{asset}")
//    private ResponseEntity<?> getOverviewByAsset(@PathVariable String asset)
//    {try{
//
//     //   List<Overview> foundAsset = overviewRepository.findByAssetType(asset);
//        if(foundAsset.isEmpty()){
//            ApiError.throwErr(404,asset + " asset Type was not found.");
//        }
//        return ResponseEntity.ok(foundAsset);
//
//    }catch(HttpClientErrorException e){
//        return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
//    }catch(Exception e){
//        return ApiError.genericApiError(e);
//    }
//
//    }












    // creating a method for the 52 year high and low both float values.

//    @PostMapping("/{symbol}")
//    private ResponseEntity<?> testUploadOverview(RestTemplate restTemplate,@PathVariable String symbol) {
//        try {
//            String apiKey = env.getProperty("AV_API_KEY");
//
//            String url = BASE_URL + "&symbol="+ symbol +"&apikey="+apiKey;
//            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);
//
//
//            System.out.println(url);
//
//            // this is going to be a saved overview
//            Overview savedOverview = overviewRepository.save(alphaVantageResponse);
//
//
//
//            //checking to see if the data is null
//            if (alphaVantageResponse == null) {
//                ApiError.throwErr(500,"Did not recieve response from AV");
//
//            } else if (alphaVantageResponse.getSymbol() == null) {
//                ApiError.throwErr(404, "Invalid Stock Symbol"+symbol);
//
//            }
//
//
//            return ResponseEntity.ok(savedOverview);
//        }
//
//        // the way that these exceptions are ordered it will check them based
//        //on the type of error that it has found
//        catch (HttpClientErrorException e) {
//            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());}
//        catch(DataIntegrityViolationException e ){
//            return ApiError.customApiError("Can Not Create duplicate stocks", 400);
//        }
//
//        catch (Exception e) {
//            return ApiError.genericApiError(e);
//        }
//    }


    // delete all OVERview from SQL database
    //return # of delete overview
    @DeleteMapping("/all")
    private ResponseEntity<?> deleteAllFromOverview(){
        try{
            // count to track the ammount of data that is deleted.
            long allOverviewCount = overviewRepository.count();
            if(allOverviewCount == 0) return  ResponseEntity.ok("No overviews to Delete");
            overviewRepository.deleteAll();

            return ResponseEntity.ok("The amount of Deleted Overviews :" +allOverviewCount);

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
            List <Overview> foundOverview = overviewRepository.findById(overviewId);
            if(foundOverview.isEmpty()){
                return ApiError.customApiError(id +" did not match any overview ", 404);
            }
            overviewRepository.deleteById(overviewId);

            return ResponseEntity.ok("Deleted overview "+foundOverview);

        }
        catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());}
         catch(NumberFormatException e){
            return  ApiError.customApiError("Invalid Id: Must be a Number", 400);


        }catch(Exception e){
            return  ApiError.genericApiError(e);
        }

    }





}