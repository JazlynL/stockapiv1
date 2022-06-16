package com.careerdevs.stockapiv1.repositories;

import com.careerdevs.stockapiv1.models.Overview;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface OverviewRepository extends JpaRepository<Overview,Long> {
    // we are able to access the exact values of all these components within the repository
     List<Overview> findById(long Id);
     List<Overview> findBySymbol(String symbol);
     List<Overview> findByExchange (String exchange);
     List<Overview> findByAssetType (String assetType);
      List<Overview> findByName(String name);
      List <Overview> findByCurrency(String currency);
     List<Overview> findByCountry(String country);
     List <Overview> findBySector(String sector);


     List<Overview> deleteById(long Id);
     List<Overview> deleteBySymbol(String symbol);
     List<Overview> deleteByAssetType (String assetType);

     List<Overview> deleteByExchange (String exchange);
     List<Overview> deleteByName(String name);
     List <Overview> deleteByCurrency(String currency);
     List<Overview> deleteByCountry(String country);
     List <Overview> deleteBySector(String sector);






    /*  public Optional <Overview> findBySymbol(String symbol);
    public List<Overview> findByAssetType( String asset);
    public  List<Overview> findByName(String name);
    public List<Overview> findByExchange (String exchange);
    public List <Overview> findByCurrency(String currency);
    public List<Overview> findByCountry(String country);
     public List <Overview> findBySector(String sector);
    public List<Overview> findByIndustry(String industry);
    public List<Overview> findBymarketCap (long MarketCap);
    public List<Overview> findByDividendDate(String dividend);
*/
}
