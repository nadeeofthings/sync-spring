// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

const monthNames = ["January", "February", "March", "April", "May", "June",
	  "July", "August", "September", "October", "November", "December"
	];
window.chartColors = {
		red: 'rgb(255, 99, 132)',
		orange: 'rgb(255, 159, 64)',
		yellow: 'rgb(255, 205, 86)',
		green: '#1CC88A',
		blue: '#4E73DF',
		purple: 'rgb(153, 102, 255)',
		grey: 'rgb(201, 203, 207)'
	};

function number_format(number, decimals, dec_point, thousands_sep) {
  // *     example: number_format(1234.56, 2, ',', ' ');
  // *     return: '1 234,56'
  number = (number + '').replace(',', '').replace(' ', '');
  var n = !isFinite(+number) ? 0 : +number,
    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
    sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
    dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
    s = '',
    toFixedFix = function(n, prec) {
      var k = Math.pow(10, prec);
      return '' + Math.round(n * k) / k;
    };
  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || '').length < prec) {
    s[1] = s[1] || '';
    s[1] += new Array(prec - s[1].length + 1).join('0');
  }
  return s.join(dec);
}

$(document).ready(function() {
	$("#start").datepicker({
		format: "dd/mm/yyyy",
		todayBtn: "linked",
	    clearBtn: true,
	    orientation: "bottom auto",
	    autoclose: true,
	    todayHighlight: true
		});
	$("#start").datepicker('setDate', new Date());
	getPrevData();
	getCurrData();
	getElecData($("#start")[0].value,$("#limit")[0].value);
	getTopUnitDataKWH();
	getTopUnitDataBTU();
});

var launch = function() {
	getElecData($("#start")[0].value,$("#limit")[0].value);
}
// Area Chart Example
var ctx = document.getElementById("myAreaChart");
var elecChart = new Chart(ctx, {
	  type: 'line',
	  data: {
	    labels: [],
	    datasets: [{
	    	label: "Electricity",
	    	customtooltiptitle: [],
	        lineTension: 0.3,
	        fill: false,
	        borderWidth: 3,
	        borderColor:  window.chartColors.green,
	        backgroundColor:  window.chartColors.green,
	        //borderColor: "rgba(0, 94, 0, 1)",
	      //BorderColor: "rgba(78, 115, 223, 1)",
	      HoverRadius: 3,
	      //HoverBackgroundColor: ["rgba(0, 94, 0, 1)","rgba(200, 115, 223, 1)","rgba(78, 115, 223, 0.05)","rgba(0, 0, 255, 0,1)","rgba(78, 115, 223, 0.05)","rgba(78, 115, 223, 0.05)","rgba(78, 115, 223, 0.05)"],
	      //HoverBorderColor: "rgba(0, 94, 0, 1)",
	      //HitRadius: 10,
	      //BorderWidth: 2,
	      data: [],
	    },{
	    	label: "AirCon",
	    	customtooltiptitle: [],
	        lineTension: 0.3,
	        fill: false,
	        borderWidth: 3,
	        borderColor:  window.chartColors.blue,
	        backgroundColor:  window.chartColors.blue,
	        //borderColor: "rgba(0, 94, 0, 1)",
	      //BorderColor: "rgba(78, 115, 223, 1)",
	      HoverRadius: 3,
	      //HoverBackgroundColor: ["rgba(0, 94, 0, 1)","rgba(200, 115, 223, 1)","rgba(78, 115, 223, 0.05)","rgba(0, 0, 255, 0,1)","rgba(78, 115, 223, 0.05)","rgba(78, 115, 223, 0.05)","rgba(78, 115, 223, 0.05)"],
	      //HoverBorderColor: "rgba(0, 94, 0, 1)",
	      //HitRadius: 10,
	      //BorderWidth: 2,
	      data: [],
	    }],
	  },
	  
	  options: {
	    maintainAspectRatio: false,
	    layout: {
	      padding: {
	        left: 10,
	        right: 25,
	        top: 25,
	        bottom: 0
	      }
	    },
	    scales: {
	      xAxes: [{
	        time: {
	          unit: 'date'
	        },
	        gridLines: {
	          display: false,
	          drawBorder: false
	        },
	        ticks: {
	          maxTicksLimit: 7
	        }
	      }],
	      yAxes: [{
	        ticks: {
	          maxTicksLimit: 5,
	          padding: 10,
	          // Include a kW in the ticks
	          callback: function(value, index, values) {
	            return number_format(value)+' kWh';
	          }
	        },
	        gridLines: {
	          color: "rgb(234, 236, 244)",
	          zeroLineColor: "rgb(234, 236, 244)",
	          drawBorder: false,
	          borderDash: [2],
	          zeroLineBorderDash: [2]
	        }
	      }],
	    },
	    legend: {
	      display: true
	    },
	    tooltips: {
	      backgroundColor: "rgb(255,255,255)",
	      bodyFontColor: "#858796",
	      titleMarginBottom: 10,
	      titleFontColor: '#6e707e',
	      titleFontSize: 14,
	      borderColor: '#dddfeb',
	      borderWidth: 1,
	      xPadding: 15,
	      yPadding: 15,
	      displayColors: false,
	      intersect: false,
	      mode: 'index',
	      caretPadding: 10,
	      callbacks: {
	    	  title: function(tooltipItem, chart) {
	              var customtitle = chart.datasets[tooltipItem[0].datasetIndex].customtooltiptitle[tooltipItem[0].index];
	      		return customtitle;
	            },
	          label: function(tooltipItem, chart) {
	            var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
	            return datasetLabel +": "+ number_format(tooltipItem.yLabel)+" kWh";
	          }
	      }
	    }
	  }
	});

//logic to get new data
var getElecData = function(date,limit) {
	var date = moment(date, "DD/MM/YYYY");
	var timestamp = date.unix();
	
	//clean everything before adding new data
	elecChart.data.datasets[0].customtooltiptitle = [];
	elecChart.data.labels = [];
	elecChart.data.datasets[0].data = [];
	elecChart.data.datasets[1].data = [];
	
  $.ajax({
    url: 'http://localhost:8080/tebbiq/rest/buildingDailyUsage?ext=kWh&limit='+limit+'&start='+timestamp+'',
    success: function(data) {
      // process your data to pull out what you plan to use to update the chart
      // e.g. new label and a new data point
    	data.forEach(function(object) {
    	      // add new label and data point to chart's underlying data structures
    		var days = [ 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday',
				'Friday', 'Saturday' ];
    		var newDate = new Date(object.timeStamp);
    		var weekday = newDate.getDay();
    		var options = { weekday: 'long'};
    		
    		
    		elecChart.data.datasets[0].customtooltiptitle.unshift(moment(newDate).format("dddd, MMMM Do YYYY"));
    		elecChart.data.labels.unshift(moment(newDate).format("DD/MM"));
    		elecChart.data.datasets[0].data.unshift(object.value);
        });
    }
  });
  $.ajax({
	    url: 'http://localhost:8080/tebbiq/rest/buildingDailyUsage?ext=BTU&limit='+limit+'&start='+timestamp+'',
	    success: function(data) {
	      // process your data to pull out what you plan to use to update the chart
	      // e.g. new label and a new data point
	    	data.forEach(function(object) {
	    	      // add new label and data point to chart's underlying data structures
	    		var days = [ 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday',
					'Friday', 'Saturday' ];
	    		var newDate = new Date(object.timeStamp);
	    		var weekday = newDate.getDay();
	    		var options = { weekday: 'long'};
	    		
	    		
	    		elecChart.data.datasets[1].customtooltiptitle.unshift(moment(newDate).format("dddd, MMMM Do YYYY"));
	    		elecChart.data.datasets[1].data.unshift(object.value*0.000293071);
	        });
	      
	        //re-render the chart
	    	elecChart.update();
	    }
	  });
};

var getAirconData = function(date,limit) {
	var date = moment(date, "DD/MM/YYYY");
	var timestamp = date.unix();
	
	//clean everything before adding new data
	//elecChart.data.datasets[0].customtooltiptitle = [];
	//elecChart.data.labels = [];
	//elecChart.data.datasets[0].data = [];
	//elecChart.data.datasets[1].data = [];
	
  
};
var getPrevData = function() {
  $.ajax({
    url: 'http://localhost:8080/tebbiq/rest/prevMonthTotal',
    success: function(data) {
      // process your data to pull out what you plan to use to update the chart
      // e.g. new label and a new data point
    	var totalKWH = 0.0;
    	var totalBTU = 0.0;
       for(var i =0; i<data.length; i++){
    	   if(data[i].ext == "kWh"){
    		   totalKWH += data[i].value;
    	   }else{
    		   totalBTU += data[i].value;
    	   }
       }
       
        var newDate = new Date(data[0].timeStamp);
		var month = monthNames[newDate.getMonth()];
       document.getElementById("prevElecMonth").innerHTML = "Electricity Consumption<br>("+month+")";

       document.getElementById("prevElecMonthValue").innerHTML = number_format(totalKWH,2,".",",")+" kWh";
       document.getElementById("prevAirMonth").innerHTML = "AC Consumption<br>("+month+")";
       document.getElementById("prevAirMonthValue").innerHTML = number_format(totalBTU,2,".",",")+" BTU";

       //currElecMonth
       //currElecMonthValue
       //prevAirMonth
       //prevAirMonthValue
      // currAirMonth
       //currAirMonthValue
    }
  });
};

//logic to get new data
var getCurrData = function() {
  $.ajax({
    url: 'http://localhost:8080/tebbiq/rest/currMonthTotal',
    success: function(data) {
      // process your data to pull out what you plan to use to update the chart
      // e.g. new label and a new data point
    	var totalKWH = 0.0;
    	var totalBTU = 0.0;
       for(var i =0; i<data.length; i++){
    	   if(data[i].ext == "kWh"){
    		   totalKWH += data[i].value;
    	   }else{
    		   totalBTU += data[i].value;
    	   }
       }
       
        var newDate = new Date(data[0].timeStamp);
		var month = monthNames[newDate.getMonth()];
       document.getElementById("currElecMonth").innerHTML = "Electricity Consumption<br>("+month+")";
       document.getElementById("currElecMonthValue").innerHTML = number_format(totalKWH,2,".",",")+" kWh";
       
       document.getElementById("currAirMonth").innerHTML = "AC Consumption<br>("+month+")";
       document.getElementById("currAirMonthValue").innerHTML = number_format(totalBTU,2,".",",")+" BTU";

       //currElecMonth
       //currElecMonthValue
       //prevAirMonth
       //prevAirMonthValue
      // currAirMonth
       //currAirMonthValue
    }
  });
};
