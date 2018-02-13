package it.eng.unipa.projectwork.web;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import it.eng.unipa.projectwork.model.Auction;
import it.eng.unipa.projectwork.service.AuctionService;
import it.eng.unipa.projectwork.service.LazyList;

/*import it.eng.book.auction.event.ClosedAuctionEvent;
import it.eng.book.auction.event.OfferAuctionEvent;
import it.eng.book.auction.event.TimerAuctionEvent;
import it.eng.book.service.AuctionEventManager;*/

@Path("/auction")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@Stateless
public class AuctionRestEndpoint {
	
	
	@EJB
	AuctionService auctionService;
	
	@GET
    @Path("/")
    public String hello(){
		return "{\"ciao\":\"ok\"}";
	}
	
	@GET
    @Path("/list")
	@RolesAllowed(value="USER")
    public LazyList<Auction> list(@QueryParam("firstResult") int firstResult,@QueryParam("maxResults") @DefaultValue("10") int maxResults){
		return auctionService.loadAuctions(firstResult, maxResults);
	}
	
	
	@GET
    @Path("/listAdmin")
	@RolesAllowed(value="ADMIN")
    public LazyList<Auction> listAdmin(){
		return auctionService.loadAuctions(0,0);
	}
	
	
	@POST
	@Path("/add")
	@RolesAllowed(value="USER")
   public Auction add(@Context SecurityContext sc,Auction auction){
		String username  = sc.getUserPrincipal().getName();
		return auctionService.add(auction,username);
	}
	
	//@Inject
	//AuctionEventManager messageManager;
	/*
   	@GET
    @Path("/offer")
    @Produces({ "application/json" })
    public String offer(@QueryParam("auctionId")String auctionId,@QueryParam("importo") BigDecimal importo) {
   		messageManager.sendAuctionEvent(new OfferAuctionEvent(auctionId, importo));
        return "{\"result\":\"OK\"}";
    }
   	
   	
   	@GET
    @Path("/ttl")
    @Produces({ "application/json" })
    public String ttl(@QueryParam("auctionId")String auctionId,@QueryParam("ttl") long ttl) {
   		messageManager.sendAuctionEvent(new TimerAuctionEvent(auctionId, ttl));
        return "{\"result\":\"OK\"}";
    }
   	
   	@GET
    @Path("/close")
    @Produces({ "application/json" })
    public String close(@QueryParam("auctionId")String auctionId) {
   		messageManager.sendAuctionEvent(new ClosedAuctionEvent(auctionId));
        return "{\"result\":\"OK\"}";
    }
    */

}
