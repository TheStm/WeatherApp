<!DOCTYPE html>
<html>
<head>
    <title>Wykres średniej temperatury</title>
    <script src="https://d3js.org/d3.v7.min.js"></script>
    <style>
        .bar {
            fill: steelblue;
        }
    </style>
</head>
<body>
<h2>Wykres średniej temperatury</h2>
<svg id="chart"></svg>

<script>
    var dataList = ${dataList}; // Przekazana lista danych z kontrolera

    // Przetwarzanie danych
    var data = dataList.map(function(obj) {
        return {
            date: obj["Dzień"],
            temperature: parseFloat(obj["Średnia dobowa temperatura  [°C]"])
        };
    });

    // Ustawienia wykresu
    var margin = { top: 20, right: 20, bottom: 30, left: 40 };
    var width = 600 - margin.left - margin.right;
    var height = 400 - margin.top - margin.bottom;

    // Tworzenie skali dla osi X i Y
    var x = d3.scaleBand()
        .range([0, width])
        .padding(0.1);
    var y = d3.scaleLinear()
        .range([height, 0]);

    // Tworzenie osi X i Y
    var xAxis = d3.axisBottom(x);
    var yAxis = d3.axisLeft(y);

    // Tworzenie wykresu
    var svg = d3.select("#chart")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    // Ustawianie domeny dla osi X i Y
    x.domain(data.map(function(d) { return d.date; }));
    y.domain([0, d3.max(data, function(d) { return d.temperature; })]);

    // Dodawanie słupków do wykresu
    svg.selectAll(".bar")
        .data(data)
        .enter().append("rect")
        .attr("class", "bar")
        .attr("x", function(d) { return x(d.date); })
        .attr("width", x.bandwidth())
        .attr("y", function(d) { return y(d.temperature); })
        .attr("height", function(d) { return height - y(d.temperature); });

    // Dodawanie osi X i Y
    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .call(xAxis);
    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis);

</script>
</body>
</html>
