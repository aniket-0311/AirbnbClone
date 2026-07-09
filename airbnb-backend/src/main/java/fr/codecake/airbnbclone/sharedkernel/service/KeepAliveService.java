package fr.codecake.airbnbclone.sharedkernel.service;

import fr.codecake.airbnbclone.listing.application.TenantService;
import fr.codecake.airbnbclone.listing.domain.BookingCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KeepAliveService {

    private static final Logger logger = LoggerFactory.getLogger(KeepAliveService.class);

    private final TenantService tenantService;

    public KeepAliveService(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    /**
     * Calls the tenant listing API every 6 hours to keep the database from going idle
     * 6 hours = 21600000 milliseconds
     * This runs on the server regardless of app usage
     */
    @Scheduled(fixedDelay = 21600000, initialDelay = 60000)
    public void keepDatabaseAlive() {
        try {
            logger.info("[KeepAlive] Starting database keep-alive call at: {}", System.currentTimeMillis());
            
            // Create a minimal pageable request
            Pageable pageable = PageRequest.of(0, 1);
            
            // Call the endpoint with default category
            tenantService.getAllByCategory(pageable, BookingCategory.BEACH);
            
            logger.info("[KeepAlive] Database keep-alive call successful at: {}", System.currentTimeMillis());
        } catch (Exception e) {
            logger.warn("[KeepAlive] Database keep-alive call failed: {}", e.getMessage(), e);
        }
    }
}
