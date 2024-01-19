import React, { useState, useEffect } from 'react';
import "chart.js/auto";
import { Line } from 'react-chartjs-2';

const GraphReward = ({ data }) => {
    const [chartData, setChartData] = useState({
      labels: [],
      datasets: [
        {
          label: 'Reward Moyen en fonction des episodes',
          data: [],
          fill: false,
          borderColor: 'rgba(75,192,192,1)',
          borderWidth: 2,
        },
      ],
    });
  
    useEffect(() => {
      // Mettez à jour le graphique chaque fois que les données changent
      setChartData((prevChartData) => ({
        ...prevChartData,
        labels: data.map((_, index) => index), // Utilisez les indices comme labels
        datasets: [
          {
            ...prevChartData.datasets[0],
            data,
          },
        ],
      }));
    }, [data]);
  
    return (
      <div>
        <Line data={chartData} />
      </div>
    );
  };
  
export default GraphReward;