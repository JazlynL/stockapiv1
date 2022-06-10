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

import java.util.List;
import java.util.Optional;

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
            Optional<Overview> foundOverview = overviewRepository.findById(idFound);
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
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<?> getOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {
        try {

            Optional<Overview> foundOverview = overviewRepository.findBySymbol(symbol);
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
    @GetMapping("/asset/{asset}")
    private ResponseEntity<?> getOverviewByAsset(@PathVariable String asset)
    {try{

        List<Overview> foundAsset = overviewRepository.findByAssetType(asset);
        if(foundAsset.isEmpty()){
            ApiError.throwErr(404,asset + " asset Type was not found.");
        }
        return ResponseEntity.ok(foundAsset);

    }catch(HttpClientErrorException e){
        return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
    }catch(Exception e){
        return ApiError.genericApiError(e);
    }

    }

    @GetMapping("/name/{name}")
    private  ResponseEntity<?> getOverviewByName(@PathVariable String name){
        try{
            Optional<Overview> foundName = overviewRepository.findByNameType(name);
            if(foundName.isEmpty()){
                ApiError.throwErr(404,name+ " name was not found.");
            }
            return ResponseEntity.ok(foundName);
        }catch(HttpClientErrorException e){
            return  ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }


    @GetMapping("/exchange/{exchange}")
    private ResponseEntity<?> getOverviewByExchange (@PathVariable String exchange){

        try{
            List<Overview> foundOverview = overviewRepository.findByExchange(exchange);

             if(foundOverview.isEmpty()){
                 ApiError.throwErr(404, exchange +" did not match any overview");
             }

             return  ResponseEntity.ok(foundOverview);

        }catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }// we will always set this exception last because its the most generic exception that can be found
        catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }


    @GetMapping("/currency/{currency}")

     private ResponseEntity<?> getOverviewByCurrency(@PathVariable String currency){
        try{
            List<Overview> foundCurrency = overviewRepository.findByCurrency(currency);

            if(foundCurrency.isEmpty()){
                ApiError.throwErr(404,currency+" currency entered was not found.");
            }
            return ResponseEntity.ok(foundCurrency);

        }catch(HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch(Exception e)
        {
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/country/{country}")

      private ResponseEntity<?> getOverviewByCountry(@PathVariable String country){
        try{
            List<Overview> foundCountry = overviewRepository.findByCountry(country);

            if (foundCountry.isEmpty()){
                ApiError.throwErr(404, country+ " country entered was not found");
            }
            return ResponseEntity.ok(foundCountry);


        }catch(HttpClientErrorException e ){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch(Exception e){
           return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/sector/{sector}")
    private ResponseEntity<?> getOverviewBySector(@PathVariable String sector){
        try{
            List<Overview> foundSector = overviewRepository.findBySector(sector);

            if(foundSector.isEmpty()){
                ApiError.throwErr(404,sector+" sector entered was not found");
            }

            return ResponseEntity.ok(foundSector);
        }catch(HttpClientErrorException e ){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/industry/{industry}")
    private ResponseEntity<?> getOverviewByIndustry(@PathVariable String industry){
        try{
            List<Overview> foundIndustry = overviewRepository.findByIndustry(industry);
            if(foundIndustry.isEmpty()){
                ApiError.throwErr(404, industry+ " industry entered was  not found");
            }
            return ResponseEntity.ok(foundIndustry);
        }catch(HttpClientErrorException e ){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/marketcap/{marketcap}")

    private ResponseEntity<?> getOverviewByMarketCap(@PathVariable String marketCap){

        try{
            Long newMarket = Long.parseLong(marketCap);
            List<Overview> foundMarketCap = overviewRepository.findByMarketCap(newMarket);

            if(foundMarketCap.isEmpty()){
                ApiError.throwErr(404, marketCap +" Market capitalization entered was not found.");
            }

            return ResponseEntity.ok(foundMarketCap);


        }catch(HttpClientErrorException e ){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }

    }
    @GetMapping("/dividend/{dividend}")
    private ResponseEntity<?> getOverviewByDividend(@PathVariable String dividend){
        try{
            List<Overview> foundDividend = overviewRepository.findbyDividend(dividend);
            if(foundDividend.isEmpty()){
                ApiError.throwErr(404, dividend+"dividened entered not found");
            }

            return  ResponseEntity.ok(foundDividend);

        }catch(HttpClientErrorException e ){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch(Exception e){
            return ApiError.genericApiError(e);
        }
    }

    // creating a method for the 52 year high and low both float values.




    @PostMapping("/{symbol}")
    private ResponseEntity<?> testUploadOverview(RestTemplate restTemplate,@PathVariable String symbol) {
        try {
            String apiKey = env.getProperty("AV_API_KEY");

            String url = BASE_URL + "&symbol="+ symbol +"&apikey="+apiKey;
            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);


            System.out.println(url);

            // this is going to be a saved overview
            Overview savedOverview = overviewRepository.save(alphaVantageResponse);



            //checking to see if the data is null
            if (alphaVantageResponse == null) {
                ApiError.throwErr(500,"Did not recieve response from AV");

            } else if (alphaVantageResponse.getSymbol() == null) {
                ApiError.throwErr(404, "Invalid Stock Symbol"+symbol);

            }


            return ResponseEntity.ok(savedOverview);
        }

        // the way that these exceptions are ordered it will check them based
        //on the type of error that it has found
        catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());}
        catch(DataIntegrityViolationException e ){
            return ApiError.customApiError("Can Not Create duplicate stocks", 400);
        }

        catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }






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
            Optional<Overview> foundOverview = overviewRepository.findById(overviewId);
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