console.log('SleepStats.js module loaded');

export function createSleepChart(containerId) {
    console.log('=== createSleepChart called ===');
    console.log('containerId:', containerId);
    console.log('typeof Chart:', typeof Chart);
    console.log('window.Chart:', window.Chart);
    
    const container = document.getElementById(containerId);
    console.log('container element:', container);
    
    if (!container) {
        console.error('Container not found!');
        return;
    }

    // Wait for Chart.js to be available
    function initChart() {
        console.log('initChart attempt, Chart available?', typeof Chart !== 'undefined');
        
        if (typeof Chart === 'undefined') {
            console.log('Chart not ready, retrying in 100ms...');
            setTimeout(initChart, 100);
            return;
        }

        console.log('Chart.js is ready! Creating chart...');

        // Create canvas element
        let canvas = container.querySelector('canvas');
        if (!canvas) {
            console.log('Creating new canvas element');
            canvas = document.createElement('canvas');
            container.appendChild(canvas);
        } else {
            console.log('Canvas already exists');
        }

        // Generate labels for last 7 days
        const labels = [];
        const today = new Date();
        for (let i = 6; i >= 0; i--) {
            const date = new Date(today);
            date.setDate(today.getDate() - i);
            // Format as "d. MMM" (e.g., "28. nov")
            const day = date.getDate();
            const month = date.toLocaleDateString('da-DK', { month: 'short' });
            labels.push(`${day}. ${month}`);
        }

        // Sample data - now with two datasets
        const data = {
            labels: labels,
            datasets: [
                {
                    label: 'Tid i seng (timer)',
                    data: [8.5, 7.5, 9, 8, 7.5, 8.5, 9],
                    backgroundColor: 'rgba(149, 165, 166, 0.6)',
                    borderColor: 'rgba(149, 165, 166, 1)',
                    borderWidth: 1
                },
                {
                    label: 'Faktisk søvntid (timer)',
                    data: [7.5, 6, 8, 7, 6.5, 7.8, 8],
                    backgroundColor: 'rgba(54, 162, 235, 0.6)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }
            ]
        };

        // Chart options
        const options = {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    max: 10,
                    title: {
                        display: true,
                        text: 'Timer'
                    }
                }
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'bottom'
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            let label = context.dataset.label || '';
                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed.y !== null) {
                                label += context.parsed.y + ' timer';
                            }
                            return label;
                        }
                    }
                }
            }
        };

        try {
            // Destroy existing chart if it exists
            if (container.chartInstance) {
                container.chartInstance.destroy();
            }
            
            // Create chart
            const chart = new Chart(canvas, {
                type: 'bar',
                data: data,
                options: options
            });
            container.chartInstance = chart;
            console.log('Chart created successfully!', chart);
        } catch (error) {
            console.error('Error creating chart:', error);
        }
    }

    initChart();
}

// Also try exposing it globally
window.createSleepChart = createSleepChart;
console.log('createSleepChart exposed globally');




console.log('SleepEffectivenessChart.js module loaded');
export function createEffectivenessChart(containerId, data) {
    console.log('=== createEffectivenessChart called ===');
    console.log('containerId:', containerId);
    console.log('data:', data);
    
    const container = document.getElementById(containerId);
    console.log('container element:', container);
    
    if (!container) {
        console.error('Container not found!');
        return;
    }

    function initChart() {
        console.log('initChart attempt, Chart available?', typeof Chart !== 'undefined');
        
        if (typeof Chart === 'undefined') {
            console.log('Chart not ready, retrying in 100ms...');
            setTimeout(initChart, 100);
            return;
        }

        console.log('Chart.js is ready! Creating effectiveness chart...');

        // Create canvas element
        let canvas = container.querySelector('canvas');
        if (!canvas) {
            console.log('Creating new canvas element');
            canvas = document.createElement('canvas');
            container.appendChild(canvas);
        } else {
            console.log('Canvas already exists');
        }

        // Process the data if it's passed, otherwise use sample data
        const chartData = data || {
            labels: ['17. okt.', '18. okt.'],
            effectiveness: [92, 92],
            feeling: [4, 5],
            latestDate: '17. okt.',
            latestEffectiveness: 92,
            latestFeeling: 4
        };

        // Chart configuration
        const config = {
            type: 'line',
            data: {
                labels: chartData.labels,
                datasets: [
                    {
                        label: 'Søvneffektivitet (%)',
                        data: chartData.effectiveness,
                        borderColor: 'rgb(54, 162, 235)',
                        backgroundColor: 'rgba(54, 162, 235, 0.1)',
                        yAxisID: 'y',
                        tension: 0.1,
                        pointRadius: 4,
                        pointHoverRadius: 6
                    },
                    {
                        label: 'Morgenfølelse (1-5)',
                        data: chartData.feeling,
                        borderColor: 'rgb(239, 83, 80)',
                        backgroundColor: 'rgba(239, 83, 80, 0.1)',
                        yAxisID: 'y1',
                        tension: 0.1,
                        pointRadius: 4,
                        pointHoverRadius: 6
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                interaction: {
                    mode: 'index',
                    intersect: false,
                },
                plugins: {
                    title: {
                        display: true,
                        text: 'Søvneffektivitet og morgenfølelse',
                        font: {
                            size: 16,
                            weight: 'normal'
                        },
                        align: 'start',
                        padding: {
                            bottom: 5
                        }
                    },
                    subtitle: {
                        display: true,
                        text: 'Udvikling i søvnkvalitet over tid',
                        font: {
                            size: 12,
                            weight: 'normal'
                        },
                        color: '#666',
                        align: 'start',
                        padding: {
                            bottom: 20
                        }
                    },
                    legend: {
                        display: true,
                        position: 'bottom',
                        labels: {
                            usePointStyle: true,
                            padding: 15
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                let label = context.dataset.label || '';
                                if (label) {
                                    label += ': ';
                                }
                                if (context.parsed.y !== null) {
                                    if (context.datasetIndex === 0) {
                                        label += context.parsed.y + '%';
                                    } else {
                                        label += context.parsed.y + '/5';
                                    }
                                }
                                return label;
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        type: 'linear',
                        display: true,
                        position: 'left',
                        min: 0,
                        max: 100,
                        ticks: {
                            stepSize: 25
                        },
                        title: {
                            display: false
                        }
                    },
                    y1: {
                        type: 'linear',
                        display: true,
                        position: 'right',
                        min: 0,
                        max: 5,
                        ticks: {
                            stepSize: 1
                        },
                        grid: {
                            drawOnChartArea: false,
                        },
                        title: {
                            display: false
                        }
                    }
                }
            }
        };

        try {
            // Destroy existing chart if it exists
            if (container.chartInstance) {
                container.chartInstance.destroy();
            }
            
            // Create new chart
            const chart = new Chart(canvas, config);
            container.chartInstance = chart;
            console.log('Effectiveness chart created successfully!', chart);
            
        } catch (error) {
            console.error('Error creating chart:', error);
        }
    }

    initChart();
}

// Expose globally
window.createEffectivenessChart = createEffectivenessChart;
console.log('createEffectivenessChart exposed globally');