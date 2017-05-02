<%@page import="java.util.HashMap"%>
<%@page import="com.prefengine.domain.SearchAttributes"%>
<%@page import="com.prefengine.dao.FlightRecordDAO"%>
<%@page import="com.prefengine.service.SearchService"%>
<%@page import="com.prefengine.service.SearchCriteria"%>
<%@page import="com.prefengine.domain.Flights"%>
<%@page import="java.util.List"%>
<%@page import="com.prefengine.domain.Itinerary"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<%@ page isELIgnored="false"%>
<html>

<head>
<title>Prefengine - The Preference based search engine</title>

<%-- <%
	//request.getAttribute("tripRecord");
	ArrayList<TripRecord> list = (ArrayList<TripRecord>)request.getAttribute("tripRecord");
	ArrayList<FlightRecord> getRouteDetails;
	for(int i=0;i<=list.size();i++){
		getRouteDetails = list.get(i).getFlightRecord();
	}
%> --%>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta name="keywords" content="Template, html, premium, themeforest" />
<meta name="description"
	content="Traveler - Premium template for travel companies">
<meta name="author" content="Tsoy">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- GOOGLE FONTS -->
<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700'
	rel='stylesheet' type='text/css'>
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,400,300,600'
	rel='stylesheet' type='text/css'>
<!-- /GOOGLE FONTS -->
<link rel="stylesheet" href="web/css/bootstrap.css">
<link rel="stylesheet" href="web/css/font-awesome.css">
<link rel="stylesheet" href="web/css/icomoon.css">
<link rel="stylesheet" href="web/css/styles.css">
<link rel="stylesheet" href="web/css/mystyles.css">
<script src="web/js/modernizr.js"></script>

</head>

<body>

	<!-- FACEBOOK WIDGET -->
	<div id="fb-root"></div>
	<script>
        (function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s);
            js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.0";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    </script>
	<!-- /FACEBOOK WIDGET -->
	<div class="global-wrap">
		<header id="main-header">
			<div class="header-top">
				<div class="container">
					<div class="row">
						<div class="col-md-3">
							<a class="logo" href="index.html"> <img
								src="web/img/new_logo_1.png" height="47px" width="150px"
								title="Image Title" />
							</a>
						</div>

						<div class="col-md-9">
							<div class="top-user-area clearfix">
								<ul class="top-user-area-list list list-horizontal list-border">
									<li class="top-user-area-avatar"><a href=""> Hello, <%=session.getAttribute("name")%></a>
									</li>
									<li><a href="#">Sign Out</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="container"></div>

		</header>
		<%
				ArrayList<Itinerary> list = (ArrayList<Itinerary>)request.getAttribute("tripRecord");
							ArrayList<Flights> getRouteDetails;
	%>


		<div class="container">
			<br> <br>
			<form method="post" action="/Prefengine/SearchController"
				name="search" class="booking-item-dates-change mb30">
				<div class="row">
					<div class="col-md-3">
						<div class="form-group form-group-icon-left">
							<i class="fa fa-map-marker input-icon input-icon-hightlight"></i>
							<label>From</label> <input class="typeahead form-control"
								name="departure" type="text"
								value="<%out.print(list.get(0).getOriginCityName());%>" />
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group form-group-icon-left">
							<i class="fa fa-map-marker input-icon input-icon-hightlight"></i>
							<label>To</label> <input type="text" name="destination"
								class="typeahead form-control"
								value="<%out.print(list.get(0).getDestinationCityName());%>" />
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group form-group-icon-left">
							<i class="fa fa-calendar input-icon input-icon-hightlight"></i> <label>Departing</label>
							<input class="date-pick form-control" id="departureDate"
								name="departureDate" data-date-format="yyyy-m-d" type="text" />
							<%-- <input class="form-control" 
								type="text" name="departureDate" value="<%out.print(list.get(0).getDepartureTime());%>"/> --%>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group form-group-icon-left">
							<i class="input-icon input-icon-hightlight"></i> <label>Passngers</label>
							<input class=" form-control" type="text"
								name="numberOfPassengers" value=1>
						</div>
					</div>
					<input type="submit" class="btn btn-primary btn-lg" value="Search">
				</div>
			</form>
			<div class="row">
				<div class="col-md-3">
					<aside class="booking-filters text-white">
						<h3>Filter By:</h3>
						<ul class="list booking-filters-list">
							<li>
								<h5 class="booking-filters-title">
									Stops <small>Price from</small>
								</h5>
								<div class="checkbox">
									<label> <%	
									SearchAttributes attributes = (SearchAttributes)request.getAttribute("searchAttr");
									HashMap<Integer,Float> stopAttr = attributes.getStops();
									for(HashMap.Entry<Integer, Float> r : stopAttr.entrySet()){
										out.print("<input class='i-check' type='checkbox' />");
										out.print(r.getKey()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); 
										out.print(r.getValue()+ "<br><br>");
									}
								
								%>
									</label>
								</div>
							</li>
							<li>
								<h5 class="booking-filters-title">Price</h5> <input type="text"
								id="price-slider">
							</li>
							<li>
								<h5 class="booking-filters-title">
									Flight Class <small>Price from</small>
								</h5>
								<div class="checkbox">
									<label> <%	
									
								HashMap<String,Float> cabinClass1 = attributes.getCoach();
								for(HashMap.Entry<String, Float> r : cabinClass1.entrySet()){
									out.print("<input class='i-check' type='checkbox' />");
									out.print(r.getKey()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); 
									out.print(r.getValue()+ "<br><br>");
								}
								%>
									</label>
								</div>

							</li>
							<li>
								<h5 class="booking-filters-title">
									Airlines <small>Price from</small>
								</h5>
								<div class="checkbox">
									<label> <!-- <input class="i-check" type="checkbox" /> -->
										<%	
									HashMap<String,Float> airlineAttr = attributes.getAirlines();
									for(HashMap.Entry<String, Float> r : airlineAttr.entrySet()){
										out.print("<input class='i-check' type='checkbox' />");
										out.print(r.getKey()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); 
										out.print(r.getValue()+ "<br><br>");
									}
								
								%>

									</label>
								</div>
							</li>
							<li>
								<h5 class="booking-filters-title">Departure Time</h5>
								<div class="checkbox">
									<label> <input class="i-check" type="checkbox" />Morning
										(5:00a - 11:59a)
									</label>
								</div>
								<div class="checkbox">
									<label> <input class="i-check" type="checkbox" />Afternoon
										(12:00p - 5:59p)
									</label>
								</div>
								<div class="checkbox">
									<label> <input class="i-check" type="checkbox" />Evening
										(6:00p - 11:59p)
									</label>
								</div>
							</li>
						</ul>
					</aside>
				</div>
				<div class="col-md-9">
					<div class="nav-drop booking-sort">
						<h5 class="booking-sort-title">
							<a href="#">Sort: Sort: Price (low to high)<i
								class="fa fa-angle-down"></i><i class="fa fa-angle-up"></i></a>
						</h5>
							<ul class="nav-drop-menu">
								<li><a href="#">Price (high to low)</a></li>
								<li><a href="#">Duration</a></li>
								<li><a href="#">Stops</a></li>
								<li><a href="#">Arrival</a></li>
								<li><a href="#">Departure</a></li>
							</ul>
					</div>

					<ul class="booking-list">
						<%
							//request.getAttribute("tripRecord");
							
							for(int i=0;i<list.size();i++){
								getRouteDetails = list.get(i).getFlightRecord();
								int stops=getRouteDetails.size()-1;
						%>
						<li>
							<div class="booking-item-container">
								<div class="booking-item">
									<div class="row">
										<div class="col-md-2">
											<div class="booking-item-airline-logo">

												<p>
													<% out.print(list.get(i).getCarrierName());
													%>
												</p>

											</div>
										</div>
										<div class="col-md-5">
											<div class="booking-item-flight-details">
												<div class="booking-item-departure">
													<i class="fa fa-plane"></i>

													<%-- <p class="booking-item-date"><% out.print(getRouteDetails.get(0).getDepartureTime());%></p> --%>
													<p style="font-size: 18px;"
														class="booking-item-destination">
														<%out.print(list.get(i).getOriginCityName()); %>
													</p>
													<h5>
														<% 
															String departureTime = getRouteDetails.get(0).getDepartureTime();
															out.print(departureTime.subSequence(0,10)+"<br> at "+departureTime.substring(12));%>
													</h5>
												</div>
												<div class="booking-item-arrival">
													<i class="fa fa-plane fa-flip-vertical"></i>

													<%-- <p class="booking-item-date"><% out.print(getRouteDetails.get(getRouteDetails.size()-1).getArrivalTime());%></p> --%>
													<p style="font-size: 18px;"
														class="booking-item-destination">
														<%out.print(list.get(i).getDestinationCityName()); %>
													</p>
													<h5>
														<%
													String arrivalTime = getRouteDetails.get(0).getArrivalTime();
													arrivalTime.substring(0, 11);
													out.print(arrivalTime.substring(0, 10)+"<br> at "+arrivalTime.substring(12));%>
													</h5>
												</div>
											</div>
										</div>
										<div class="col-md-2">
											<%
												float minutes = list.get(i).getTotalDuration();
												int hours = Math.round(minutes/60);
												int remainingMinutes =Math.round(minutes%60);
											
											%>
											<h5>
												<%out.print(hours+"h "+remainingMinutes +"m"); %>
											</h5>
											<p>
												<%out.print("Stops: "+stops); %>
											</p>
										</div>
										<div class="col-md-3">
											<span class="booking-item-price">
												<%out.print(" $ "+list.get(i).getPrice()); %>
											</span><span>/person</span> <br> <br>
											<p class="booking-item-flight-class">
												<%out.print("Class Type: "+list.get(i).getCoach()); %>
											</p>
											<a class="btn btn-primary" href="#">Select</a>

										</div>
									</div>
								</div>

								<div class="booking-item-details">
									<div class="row">
										<div class="col-md-8">

											<p>Flight Details</p>
											<% for(int j=0;j<getRouteDetails.size();j++){
											%>
											<h5 class="list-title">

												<b>From : </b>
												<%out.print("<b>"+getRouteDetails.get(j).getAirportDeparture().getAirportCity()+"</b> ( "+ getRouteDetails.get(j).getAirportArrival().getAirportName()+" Airport ) <br>");%>
												<b>To : </b>
												<%out.print("<b>"+getRouteDetails.get(j).getAirportArrival().getAirportCity()+"</b> ( "+ getRouteDetails.get(j).getAirportDeparture().getAirportName()+" Airport )"); %>


											</h5>
											<ul class="list">
												<li>Airline : <%out.print(getRouteDetails.get(j).getCarrier().getCarrierName()+" "+getRouteDetails.get(j).getFlightNumber()); %></li>
												<li>Coach Class <% out.print(getRouteDetails.get(j).getCabin());%></li>
												<li>Departure Time :<%out.print(getRouteDetails.get(j).getDepartureTime()); %>
													Arrival Time <%out.print(getRouteDetails.get(j).getArrivalTime()); %></li>
												<li>Flight Duration <%out.print(getRouteDetails.get(j).getOnAirTime()/60 +" hours " +getRouteDetails.get(j).getOnAirTime()%60+ " minutes"); %></li>
											</ul>
											<h5>
												<%if(j<getRouteDetails.size()-1){out.print("Layover: "+getRouteDetails.get(j).getLayoverDuration());} else {out.print("");} %>
											</h5>
											<%
												}	 
											%>
										</div>
									</div>
								</div>
							</div> <%
							}%>

						</li>




					</ul>
					<p class="text-right">
						Not what you're looking for? <a class="popup-text"
							href="#search-dialog" data-effect="mfp-zoom-out">Try your
							search again</a>
					</p>
				</div>
			</div>
			<div class="gap"></div>
		</div>



		<footer id="main-footer">
			<div class="container">
				<div class="row row-wrap">
					<div class="col-md-3">
						<a class="logo" href="index.html"> <img
							src="img/logo-invert.png" alt="Image Alternative text"
							title="Image Title" />
						</a>
						<p class="mb20">Booking, reviews and advices on hotels,
							resorts, flights, vacation rentals, travel packages, and lots
							more!</p>
						<ul class="list list-horizontal list-space">
							<li><a
								class="fa fa-facebook box-icon-normal round animate-icon-bottom-to-top"
								href="#"></a></li>
							<li><a
								class="fa fa-twitter box-icon-normal round animate-icon-bottom-to-top"
								href="#"></a></li>
							<li><a
								class="fa fa-google-plus box-icon-normal round animate-icon-bottom-to-top"
								href="#"></a></li>
							<li><a
								class="fa fa-linkedin box-icon-normal round animate-icon-bottom-to-top"
								href="#"></a></li>
							<li><a
								class="fa fa-pinterest box-icon-normal round animate-icon-bottom-to-top"
								href="#"></a></li>
						</ul>
					</div>

					<div class="col-md-3">
						<h4>Newsletter</h4>
						<form>
							<label>Enter your E-mail Address</label> <input type="text"
								class="form-control">
							<p class="mt5">
								<small>*We Never Send Spam</small>
							</p>
							<input type="submit" class="btn btn-primary" value="Subscribe">
						</form>
					</div>
					<div class="col-md-2">
						<ul class="list list-footer">
							<li><a href="#">About US</a></li>
							<li><a href="#">Press Centre</a></li>
							<li><a href="#">Best Price Guarantee</a></li>
							<li><a href="#">Travel News</a></li>
							<li><a href="#">Jobs</a></li>
							<li><a href="#">Privacy Policy</a></li>
							<li><a href="#">Terms of Use</a></li>
							<li><a href="#">Feedback</a></li>
						</ul>
					</div>
					<div class="col-md-4">
						<h4>Have Questions?</h4>
						<h4 class="text-color">+1-202-555-0173</h4>
						<h4>
							<a href="#" class="text-color">support@traveler.com</a>
						</h4>
						<p>24/7 Dedicated Customer Support</p>
					</div>

				</div>
			</div>
		</footer>

		<script src="web/js/jquery.js"></script>
		<script src="web/js/bootstrap.js"></script>
		<script src="web/js/slimmenu.js"></script>
		<script src="web/js/bootstrap-datepicker.js"></script>
		<script src="web/js/bootstrap-timepicker.js"></script>
		<script src="web/js/nicescroll.js"></script>
		<script src="web/js/dropit.js"></script>
		<script src="web/js/ionrangeslider.js"></script>
		<script src="web/js/icheck.js"></script>
		<script src="web/js/fotorama.js"></script>
		<script
			src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
		<script src="web/js/typeahead.js"></script>
		<script src="web/js/card-payment.js"></script>
		<script src="web/js/magnific.js"></script>
		<script src="web/js/owl-carousel.js"></script>
		<script src="web/js/fitvids.js"></script>
		<script src="web/js/tweet.js"></script>
		<script src="web/js/countdown.js"></script>
		<script src="web/js/gridrotator.js"></script>
		<script src="web/js/custom.js"></script>

		<script type="text/javascript">
			var rowCount = 1;
			//Added code by Yinka
			function addNewRow(){
					rowCount += 1;
					$('#nonfuncarea').append("<div class='row'> "+
							"<div class='col-md-4'> " +
							"	<div class='form-group form-group-lg'> " +
							"		<label>Operator</label> " +
							"		<input type='radio' name='operator" + rowCount + "' value='and' checked> AND " +
							"		<input type='radio' name='operator" + rowCount + "' value='or'> OR " +
							"		<input type='radio' name='operator" + rowCount + "' value='compromise'> COMPROMISE " +
							"	</div> " +
							"</div> " +
							"<div class='col-md-4'> " +
							
							"<div class='form-group form-group-lg'> <label>Non-Functional Requirement</label> " +
							
							"<select name='req" + ((rowCount * 2)-1) + "' value='Price, stops, duration...' class='form-control'> " +
																				  "<option value='price'>Price</option> " +
																				  "<option value='stops'>Stops</option> " +
																				  "<option value='duration'>Duration</option> " +
																				  "<option value='mileage'>Mileage</option> " +
																				"</select> " +
																			"</div> " +
																			
																		"</div> " +
																		/* "<div class='col-md-3'> " +
																		"	<div class='form-group form-group-lg'> " +
																		"		<label>Operator</label> " +
																		"		<input type='radio' name='operator" + rowCount + "' value='and' checked> AND " +
																		"		<input type='radio' name='operator" + rowCount + "' value='or'> OR " +
																		"		<input type='radio' name='operator" + rowCount + "' value='compromise'> COMPROMISE " +
																		"	</div> " +
																		"</div> " + */
																		/* "<div class='col-md-4'> " +
																		"	<div class='form-group form-group-lg'> " +
																	"			<label>Non-Functional Requirement</label> " +
																	"			<select name='req" + (rowCount * 2) + "' value='Price, stops, duration...' class='form-control'> " +
																	"			  <option value='price'>Price</option> " +
																	"			  <option value='stops'>Stops</option> " +
																	"			  <option value='duration'>Duration</option> " +
																	"			  <option value='mileage'>Mileage</option>  " +
																	"			</select>	 " + */																
																	"		</div>  " +
																	"	</div> " +
																	"</div> " 
																	
																	);
					return false;
				}
			$(document).ready(function() {
				var date = new Date();
				var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
				$('#departureDate').datepicker('setStartDate', 'today', 'dateFormat', 'yy-mm-dd');
				$('#returnDate').datepicker('setStartDate', 'today');
		
				
				$('#departureDate').datepicker()
				  .on('changeDate', function(ev){
					  $("#returnDate").val($("#departureDate").datepicker('getFormattedDate'));
					  $('#returnDate').datepicker('setStartDate', val($("#departureDate").datepicker('getFormattedDate')))
					//if (ev.date.valueOf() < startDate.valueOf()){
					  //....
				  });
			});
		</script>

	</div>
</body>

</html>


