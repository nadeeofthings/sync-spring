// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

// Pie Chart Example
var ctx = document.getElementById("myPieChart");
var topChart = new Chart(ctx, {
  type: 'doughnut',
  data: {
    labels: [],
    datasets: [{
      data: [],
      labels: [],
      backgroundColor: ['#B7F5E1', '#70EBBE','#1CC88A'],
      hoverBackgroundColor: ['#93F0D3', '#4CE6AD','#19B37A'],
      hoverBorderColor: "rgba(234, 236, 244, 1)",
      borderWidth: 3
    },
    {
        data: [],
        labels: [],
        backgroundColor: ['#BBC9F2', '#7591E6','#4E73DF'],
        hoverBackgroundColor: ['#98ADEC', '#5376DF','#305AD9'],
        hoverBorderColor: "rgba(234, 236, 244, 1)",
        borderWidth: 3
      }],
  },
  options: {
    maintainAspectRatio: false,
    tooltips: {
      backgroundColor: "rgb(255,255,255)",
      bodyFontColor: "#858796",
      borderColor: '#dddfeb',
      borderWidth: 1,
      xPadding: 15,
      yPadding: 15,
      displayColors: false,
      caretPadding: 10,
      callbacks: {
        	label: function(tooltipItem, data) {
          	var dataset = data.datasets[tooltipItem.datasetIndex];
            var index = tooltipItem.index;
            return dataset.labels[index] + ': ' +number_format(dataset.data[index],1,".",",")+"%" ;
          }
        },
    },
    legend: {
      display: false
    },
    cutoutPercentage: 50,
  },
});

//logic to get new data
var getTopUnitDataKWH = function() {
	topChart.data.datasets[0].labels=[];
	topChart.data.datasets[0].data=[];
	var total = 0;
		
  $.ajax({
    url:  contextPath+'/rest/topUnitsThisWeek?ext=kWh',
    dataType: 'json',
    success: function(data) {
      // process your data to pull out what you plan to use to update the chart
      // e.g. new label and a new data point
    	for(i=0;i<3;i++){
    		total+=data[i].value;
    	}
    	for(i=0;i<3;i++){
    		topChart.data.datasets[0].labels.unshift(data[i].id+" floor office "+data[i].unit);
    		topChart.data.datasets[0].data.unshift((100/total)*data[i].value);
    	}
    	
    	//re-render the chart
    	topChart.update();
   },
    error: function(jqXHR, exception) {
        window.location.href = contextPath;
    }
  });
};

var getTopUnitDataBTU = function(){
	topChart.data.datasets[1].labels=[];
	topChart.data.datasets[1].data=[];
	var total = 0;
	
	$.ajax({
		    url: contextPath+'/rest/topUnitsThisWeek?ext=BTU',
        dataType: 'json',
		    success: function(data) {
		      // process your data to pull out what you plan to use to update the chart
		      // e.g. new label and a new data point
		    	for(i=0;i<3;i++){
		    		total+=data[i].value;
		    	}
		    	for(i=0;i<3;i++){
		    		topChart.data.datasets[1].labels.unshift(data[i].id+" floor office "+data[i].unit);
		    		topChart.data.datasets[1].data.unshift((100/total)*data[i].value);
		    	}
		    	
		        //re-render the chart
		    	topChart.update();
		    },
        error: function(jqXHR, exception) {
            window.location.href = contextPath;
        }
		  });
}

