<!DOCTYPE HTML>
<html>

<head>
<title>Prefengine - Welcome</title>


<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta name="keywords" content="Template, html, premium, themeforest" />
<meta name="description"
	content="Prefengine-The Preference based search engine">
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
<style>
.hidden {
	display: none;
}
</style>
<script type="text/javascript">
    	function showHide(){
    		//alert("here");
    		var checkbox = document.getElementById("show");
    		var hide = document.getElementById("hidden");
    		if(checkbox.checked){
    			hide.style.display="block";
    		}
    		else{
    			hide.style.display="none";
    		}
    		//alert("done");
    	}
    </script>


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
						<div class="col-md-12" align="center">
							<a class="logo" href="index.html"> <img
								src="web/img/new_logo_1.png" height="47px" width="150px"
								alt="Image Alternative text" title="Image Title" />
							</a>
						</div>

						<%-- <div class="col-md-9">
							<div class="top-user-area clearfix">
								<ul class="top-user-area-list list list-horizontal list-border">
									<li class="top-user-area-avatar"><a href=""> Hello, <%=session.getAttribute("name")%></a>
									</li>
									<li><a href="#">Sign Out</a></li>
								</ul>
							</div>
						</div> --%>
					</div>
				</div>
			</div>
		</header>

		<div class="show-onload">
			<div class="bg-holder full">
				<div class="bg-mask"></div>
				<div class="bg-img"
					style="background-image: url(web/img/bg_image.jpg);">
				</div>
				<div class="bg-content">
					<div class="container">
						<div class="row">
							<div class="col-md-12">
								<div class="search-tabs search-tabs-bg mt50">

									<div class="tabbable">

										<div class="tab-content">

											<div class="tab-pane fade in active" id="tab-1">
												<h2>Search for Cheap Flights</h2>
												<form method="post" action="/Prefengine/SearchController"
													name="search">
													<div class="tabbable">
														<ul class="nav nav-pills nav-sm nav-no-br mb10"
															id="flightChooseTab">
															<!-- <li class="active"><a href="#flight-search-1"
																data-toggle="tab">Round Trip</a></li> -->
															<li class="active"> <a href="#flight-search-2" data-toggle="tab">One
																	Way</a></li>
														</ul>
														
														<div class="tab-content">
															<div class="tab-pane fade in active" id="flight-search-1">
																<div class="row">
																	<div class="col-md-3">
																		<div
																			class="form-group form-group-lg form-group-icon-left">
																			<i class="fa fa-map-marker input-icon"></i> <label>From</label>
																			<input class="typeahead form-control"
																				placeholder="City, Airport, U.S. Zip" type="text"
																				value="BOS" name="departure" />
																		</div>
																	</div>
																	<div class="col-md-3">
																		<div
																			class="form-group form-group-lg form-group-icon-left">
																			<i class="fa fa-map-marker input-icon"></i> <label>To</label>
																			<input class="typeahead form-control"
																				placeholder="City, Airport, U.S. Zip" type="text"
																				value="AMD" name="destination" />
																		</div>
																	</div>
																	<div class="input-daterange" data-date-format="M d, D">
																	<div class="row">
																		<div class="col-md-3">
																			<div
																				class="form-group form-group-lg form-group-icon-left">
																				<i
																					class="fa fa-calendar input-icon input-icon-highlight"></i>
																				<label>Departing</label> <input class="date-pick form-control" id="departureDate"
																					name="departureDate" data-date-format="yyyy-m-d"  type="text" />
																			</div>
																		</div>
																		<!-- <div class="col-md-3">
																			<div
																				class="form-group form-group-lg form-group-icon-left">
																				<i
																					class="fa fa-calendar input-icon input-icon-highlight"></i>
																				<label>Returning</label> <input class="date-pick form-control" id="returnDate"
																					name="returnDate" data-date-format="yyyy-m-d"  type="text" />
																			</div>
																		</div> -->
																		<div class="col-md-3">
																			<div
																				class="form-group form-group-lg form-group-icon-left">
																				<label>Passngers</label>
																				<div class="btn-group btn-group-select-num"
																					data-toggle="buttons">
																					<input class="form-control" type="text"
																						name="numberOfPassengers" value=1>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
																</div>
																
															</div>
															<div class="tab-pane fade" id="flight-search-2">
																<div class="row">
																	<div class="col-md-6">
																		<div
																			class="form-group form-group-lg form-group-icon-left">
																			<i class="fa fa-map-marker input-icon"></i> <label>From</label>
																			<input class="typeahead form-control"
																				placeholder="City, Airport, U.S. Zip" type="text" />
																		</div>
																	</div>
																	<div class="col-md-6">
																		<div
																			class="form-group form-group-lg form-group-icon-left">
																			<i class="fa fa-map-marker input-icon"></i> <label>To</label>
																			<input class="typeahead form-control"
																				placeholder="City, Airport, U.S. Zip" type="text" />
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-md-3">
																		<div
																			class="form-group form-group-lg form-group-icon-left">
																			<i
																				class="fa fa-calendar input-icon input-icon-highlight"></i>
																			<label>Departing</label> <input
																				class="date-pick form-control"
																				data-date-format="M d, D" type="text" />
																		</div>
																	</div>
																	<div class="col-md-6">
																		<div
																			class="form-group form-group-lg form-group-icon-left">
																			<label>Passengers</label>
																			<div class="btn-group btn-group-select-num"
																				data-toggle="buttons">
																				<input class="form-control" type="text"
																					name="numberOfPassengers" value=1>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															
															<input type="checkbox" name="checkbox"
																onclick="showHide()" id="show" />
															<lable for="show">Show more options</lable>
															<br>
															<br>
															

															<div class="content hideContent" hidden="true"
																id="hidden">
															<div id="nonfuncarea">
																	<div id="req1" class="row buttonHolder">
																		<h3>Advanced Non-Functional Parameters</h3>
																		<div class="col-md-4">
																			<input type="hidden" name="count" id="count" value ="1">
																			<div class="form-group form-group-lg">
																				<select name="req" class="form-control">
																				  <option value="s_price">Price</option>
																				  <option value="s_stops">Stops</option>
																				  <option value="s_duration">Duration</option>
																				  <option value="s_rank">Rank</option>
																				</select>															
																			</div>
																		</div>
																		<div class="col-md-1 addButton">
																			<div class="form-group form-group-lg" style="padding-top:12px;">
																				
																					<a href="#" onclick="return addNewRow();" style="font-size:17px;"><i class="fa fa-plus" aria-hidden="true"></i> &nbsp;&nbsp;&nbsp;Add</a>
																				
																			</div>
																		</div>
																		<input name="nonfunctionalparameters" type="hidden" value="">
															<!-- </div>  -->
															
														</div>
													</div>
															
															
																<div class="row">
																<!-- 	<div class="col-md-2">
																		<h5 class="booking-filters-title">Stops</h5>
																		<div class="checkbox">
																			<label> <input class="i-check" type="checkbox"
																				value="0" name="stops" />Non-stop
																			</label>
																		</div>
																		<div class="checkbox">
																			<label> <input class="i-check" type="checkbox"
																				value="1" name="stops" />1 Stop
																			</label>
																		</div>
																		<div class="checkbox">
																			<label> <input class="i-check" type="checkbox"
																			value="2"	name="stops" />2+ Stops
																			</label>
																		</div>
																	</div> -->
																	<!-- <div class="col-md-1"></div> -->
																<!-- Min max range entered by user!  -->
																<div align="center"><b><p style="color:RED"> User must specify the range of all the parameters to get best result </p></b> </div>
																<div class="col-md-2">
																	<h5 class="booking-filters-title">Price</h5>
																	<input type="text" id="price-slider" name="price">
																</div>
																<div class="col-md-1"></div>
																<div class="col-md-2">
																	<h5 class="booking-filters-title">Stops</h5>
																	<input type="text" id="stops-slider" name="stops">
																</div>
																<div class="col-md-1"></div>
																<div class="col-md-2">
															
																	<h5 class="booking-filters-title">Duration</h5>
																	<input type="text" id="duration-slider" name="duration">
																</div>
																<div class="col-md-1"></div>
																<div class="col-md-2">
															
																	<h5 class="booking-filters-title">Airline Rank</h5>
																	<input type="text" id="rank-slider" name="rank">
																</div>
																
																<!-- End  -->
																
																<!-- <div class="col-md-1"></div> -->
																<!-- <div class="col-md-3">
																	<h5 class="booking-filters-title">Flight Class</h5>
																	<div class="checkbox">
																		<label> <input class="i-check" type="checkbox"
																			value="COACH" name=cabin />Economy
																		</label>
																	</div>
																	<div class="checkbox">
																		<label> <input class="i-check" type="checkbox"
																			value="BUSINESS"	name="cabin" />Business
																		</label>
																	</div>
																	<div class="checkbox">
																		<label> <input class="i-check" type="checkbox"
																		value="FIRST"	name="cabin" />First
																		</label>
																	</div>
																</div> -->
																<!-- <div class="col-md-1"></div> -->
															</div>
																	
																  <br><br>
														</div>
													</div>
													<button class="btn btn-primary btn-lg" type="submit">Search
														for Flights</button>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
					<footer id="main-footer">
			<div class="container">
				<div class="row row-wrap">
					
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
			//var operandCount = 1;
			var rowCount = 1;
			//Added code by Yinka
			function addNewRow(){
				document.getElementById('count').value = rowCount;
				
				//This removes the add button
				$( "div" ).remove(".addButton");
				
				var previousOperandRow = "req" + rowCount;
				
				$('#' + previousOperandRow).append("<div class='col-md-4'> " +
							"	<div class='form-group form-group-lg'> " +
							"		<label>Select Operator</label> " +
							"		<input type='radio' name='operator" + rowCount + "' value='LEAST' checked> AND " +
							"		<input type='radio' name='operator" + rowCount + "' value='GREATEST'> OR " +
							"		<input type='radio' name='operator" + rowCount + "' value=''> COMPROMISE " +
							"	</div> " +
							"</div> " +
							"</div>" );

				rowCount += 1;
					$('#nonfuncarea').append(
							"<div class='row' id='req" + rowCount + "'> " +
								"<div class='col-md-4'> " +
							
									"<div class='form-group form-group-lg'> <label>Non-Functional Requirement</label> " +
							
										"<select name='req' value='Price, stops, duration...' class='form-control'> " +
																				  "<option value='s_price''>Price</option> " +
																				  "<option value='s_stops'>Stops</option> " +
																				  "<option value='s_duration'>Duration</option> " +
																				  "<option value='s_rank'>Rank</option> " +
										"</select> " +
									"</div> " +						
								"</div> " +
								
								
																		
								"<div class='col-md-1 addButton'>" +
									"<div class='form-group form-group-lg'>" +
										" <br> <br>"+
										"<a href='#' onclick='return addNewRow();' style='font-size:17px;'><i class='fa fa-plus' aria-hidden='true'></i> &nbsp;&nbsp;&nbsp;Add</a> " +
										"</label>" +
									"</div>" +
								"</div>" +
																		
							"</div>  " );
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
					
				  });
			
			});
		</script>
	</div>
</body>

</html>


