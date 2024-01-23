import React, { useState, useEffect } from 'react';
import "chart.js/auto";
import { Line } from 'react-chartjs-2';

const GraphReward = ({ data }) => {
    const [chartData, setChartData] = useState({
      labels: [],
      datasets: [
        {
          label: 'Average reward per episode',
          data: [],
          fill: false,
          borderColor: 'rgba(75,192,192,1)',
          borderWidth: 2,
        },
      ],
    });

    const chartOptions = {
      scales: {
          x: {
              ticks: {
                  color: 'white',
                  font: {
                    size: 14,
                    weight: 'bold'
                }
              },
              grid: {
                  color: 'rgba(255, 255, 255, 0.1)'
              }
          },
          y: {
              ticks: {
                  color: 'white',
                  font: {
                    size: 14, 
                    weight: 'bold'
                },
                beginAtZero: true,
              },
              grid: {
                  color: 'rgba(255, 255, 255, 0.1)'
              },
              min: -0.5,
              max: 2
          }
      },
      plugins: {
          legend: {
              labels: {
                  color: 'white',
                  font: {
                    size: 14,
                    weight: 'bold'
                }
              }
          },
          tooltip: {
            titleFont: {
              size: 14,
              weight: 'bold'
            },
            bodyFont: {
                size: 14,
                weight: 'bold' 
            },
            titleFontColor: 'white', 
            bodyFontColor: 'white',
          }
      }
  };
  
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
        <Line data={chartData} options={chartOptions}/>
      </div>
    );
  };
  
export default GraphReward;