package com.careerdevs.stockapiv1.repositories;

import com.careerdevs.stockapiv1.models.Overview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OverviewRepository extends CrudRepository<Overview,Long> {
    // we are able to access the exact values of all these components within the repository.
    public Optional <Overview> findBySymbol(String symbol);
    public List<Overview> findByAssetType( String asset);
    public  Optional<Overview> findByNameType(String name);
    public List<Overview> findByExchange (String exchange);
    public List <Overview> findByCurrency(String currency);
    public
}
