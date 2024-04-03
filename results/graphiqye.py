import pandas as pd
import matplotlib.pyplot as plt
import glob
import os

def print_stats(data, title):
    print(f"----- {title} -----")
    print("Mean values for each state:")
    print(data.mean())
    print("\nMaximum values for each state:")
    print(data.max())
    print("\nMinimum values for each state:")
    print(data.min())
    print("\nStandard deviation for each state:")
    print(data.std())
    print("-------------------------------------------\n")

def plot_individual_runs(dataframes):
    plt.figure(figsize=(12, 6))
    for i, df in enumerate(dataframes):
        alpha = 0.1 if i > 0 else 1  # Full opacity for the first line to show legend
        plt.plot(df['Susceptible'], color='blue', alpha=alpha, label='Susceptible' if i == 0 else "")
        plt.plot(df['Exposed'], color='orange', alpha=alpha, label='Exposed' if i == 0 else "")
        plt.plot(df['Infected'], color='red', alpha=alpha, label='Infected' if i == 0 else "")
        plt.plot(df['Recovered'], color='green', alpha=alpha, label='Recovered' if i == 0 else "")
    plt.title('Individual Simulation Runs for SEIR Model')
    plt.xlabel('Time in days')
    plt.ylabel('Number of agents in each state')
    plt.legend()
    
    plt.show()

    # Print statistics for individual runs
    all_runs_data = pd.concat(dataframes)
    print_stats(all_runs_data, "Statistics for Individual Runs")

def plot_average_curve(mean_data):
    plt.figure(figsize=(12, 6))
    plt.plot(mean_data['Susceptible'], color='blue', label='Susceptible')
    plt.plot(mean_data['Exposed'], color='orange', label='Exposed')
    plt.plot(mean_data['Infected'], color='red', label='Infected')
    plt.plot(mean_data['Recovered'], color='green', label='Recovered')
    plt.title('Average SEIR Model Over Time')
    plt.xlabel('Time in days')
    plt.ylabel('Average number of agents in each state')
    plt.legend()
    plt.grid(True)
    plt.show()

    # Print statistics for the average curve
    print_stats(mean_data, "Statistics for Average Curve")

def process_SEIR_data(csv_folder_path):
    # Find all CSV files in the specified directory
    csv_files = glob.glob(os.path.join(csv_folder_path, "SEIR*.csv"))
    if not csv_files:
        print("No CSV files found in the specified directory.")
        return None, None
    
    dataframes = []
    
    # Read each CSV file and append to list
    for file in csv_files:
        try:
            df = pd.read_csv(file, header=None)
            df.columns = ['Susceptible', 'Exposed', 'Infected', 'Recovered']
            dataframes.append(df)
        except pd.errors.EmptyDataError:
            print(f"Warning: The file {file} is empty and will be skipped.")
        except Exception as e:
            print(f"An error occurred while reading {file}: {e}")

    if not dataframes:
        print("No data to plot.")
        return None, None
    
    # Calculate the mean along the columns, which gives us the average number of agents in each state for each iteration
    mean_data = pd.concat(dataframes).groupby(level=0).mean()

    return dataframes, mean_data

# Assuming the CSV files are in the folder 'SEIR_csv_data' in the current directory
csv_folder_path = os.path.dirname(os.path.realpath(__file__))

# Call the function and process the CSV files
dataframes, mean_data = process_SEIR_data(csv_folder_path)
if dataframes is not None and mean_data is not None:
    plot_individual_runs(dataframes)
    plot_average_curve(mean_data)
