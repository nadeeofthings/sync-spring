// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

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

// Area Chart Example
var ctx = document.getElementById("ElecChart");
var elecChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [{
    	label: "Consumption",
        lineTension: 0.3,
        fill: false,
        borderWidth: 3,
        borderColor: "rgba(28, 200, 138, 1)",
        backgroundColor: "rgba(28, 200, 138, 1)",
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
      display: false
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
        label: function(tooltipItem, chart) {
          var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
          return datasetLabel +":"+ number_format(tooltipItem.yLabel)+" kWh";
        }
      }
    }
  }
});

//logic to get new data
var getElecData = function(id,unit) {
  $.ajax({
    url: 'http://localhost:8080/tebbiq/rest/byId?ext=kWh&id='+id+'&unit='+unit,
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
    		
    		elecChart.data.labels.push(days[weekday]);
    		elecChart.data.datasets[0].data.push(object.value);
        });
      
      // re-render the chart
    	elecChart.update();
    }
  });
};
