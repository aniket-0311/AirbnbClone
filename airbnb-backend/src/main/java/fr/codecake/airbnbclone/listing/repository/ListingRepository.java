package fr.codecake.airbnbclone.listing.repository;

import fr.codecake.airbnbclone.listing.application.dto.DisplayCardListingDTO;
import fr.codecake.airbnbclone.listing.application.dto.sub.CoverPictureDTO;
import fr.codecake.airbnbclone.listing.application.dto.vo.PriceVO;
import fr.codecake.airbnbclone.listing.domain.BookingCategory;
import fr.codecake.airbnbclone.listing.domain.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query("SELECT listing FROM Listing listing LEFT JOIN FETCH listing.pictures picture" +
            " WHERE listing.landlordPublicId = :landlordPublicId AND picture.isCover = true")
    List<Listing> findAllByLandlordPublicIdFetchCoverPicture(UUID landlordPublicId);

    long deleteByPublicIdAndLandlordPublicId(UUID publicId, UUID landlordPublicId);

    @Query("SELECT new fr.codecake.airbnbclone.listing.application.dto.DisplayCardListingDTO(" +
            "new fr.codecake.airbnbclone.listing.application.dto.vo.PriceVO(l.price), " +
            "l.location, " +
            "new fr.codecake.airbnbclone.listing.application.dto.sub.CoverPictureDTO(p.fileContentType), " +
            "l.bookingCategory, " +
            "l.publicId) " +
            "FROM Listing l LEFT JOIN l.pictures p " +
            "WHERE p.isCover = true AND l.bookingCategory = :bookingCategory")
    Page<DisplayCardListingDTO> findAllByBookingCategoryWithCoverOnly(
            Pageable pageable,
            @Param("bookingCategory") BookingCategory bookingCategory
    );

    @Query("SELECT new fr.codecake.airbnbclone.listing.application.dto.DisplayCardListingDTO(" +
            "new fr.codecake.airbnbclone.listing.application.dto.vo.PriceVO(l.price), " +
            "l.location, " +
            "new fr.codecake.airbnbclone.listing.application.dto.sub.CoverPictureDTO(p.fileContentType), " +
            "l.bookingCategory, " +
            "l.publicId) " +
            "FROM Listing l LEFT JOIN l.pictures p " +
            "WHERE p.isCover = true")
    Page<DisplayCardListingDTO> findAllWithCoverOnly(Pageable pageable);

    Optional<Listing> findByPublicId(UUID publicId);

    List<Listing> findAllByPublicIdIn(List<UUID> allListingPublicIDs);

    Optional<Listing> findOneByPublicIdAndLandlordPublicId(UUID listingPublicId, UUID landlordPublicId);

    Page<Listing> findAllByLocationAndBathroomsAndBedroomsAndGuestsAndBeds(
            Pageable pageable, String location, int bathrooms, int bedrooms, int guests, int beds
    );
}
