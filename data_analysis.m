close all; clear all; warning('off','all')

%% Data Analysis
%   Make sure to run the .java files first in order to generate
%   data on the .txt files

%% First step: Data retrieval and cleaning
% Loads the files
consumer_file = fopen('MSConsumer.txt');
corporate_file = fopen('MSCorporate.txt');

% Extract the data from the files and store them in vectors
avg_consumer = get_data_from_file(consumer_file)
avg_corp = get_data_from_file(corporate_file)

%% Find average waiting time and confidence interval

disp(['Average waiting time (consumer): ', num2str(avg_consumer(1))]);
disp(['Average waiting time (corporate): ', num2str(avg_corp(1))]);

% Confidence Interval
% Say we want 95% Confidence Interval, we consider positive the values of
% range [0.025, 0.975]
r1 = 0.025;
r2 = 0.975;

n1 = length(avg_consumer)
n2 = length(avg_corp)

% Find the t-inverse function
pkg load statistics;
t_inverse_1 = tinv([r1 r2], n1-1);
t_inverse_2 = tinv([r1 r2], n2-1);

% Find the variances of both samples
var_consumers = var(avg_consumer)
var_corporate = var(avg_corp)

% STD of the means of both sampls
std_mean_cons = sqrt(var_consumers/n1);
std_mean_corp = sqrt(var_corporate/n2);

% Confidence Intervals
Conf_Interv_cons = avg_consumer + t_inverse_1*std_mean_cons
Conf_Interv_corp = avg_corp + t_inverse_2*std_mean_corp

%% Replication/ Deletion Approach
% This function uses the Welch method
%% TODO

%% Functions
function average = find_average(arrival, start)
    sum = 0;
    for index = 1: length(arrival)
        waiting_time = start(index) - arrival(index);
        sum = sum + waiting_time;
    end
    average = sum / length(arrival);
end

function avg_sim1 = get_data_from_file(file_name)
    line = fgetl(file_name);
    creation = []; start =[]; finish = []; avg_sim1 = []; first = false;
    while ischar(line)
     if (strfind(line, 'simulation'))
         if(first)
         avg_sim1 = [avg_sim1 find_average(creation, start)];
         creation = []; start =[]; finish = [];
         end
      first= true;
     elseif strfind(line, 'Creation')
         creation = [creation get_number(line)];
     elseif strfind(line, 'Production started')
         start = [start get_number(line)];
     elseif strfind(line, 'Production complete')
         finish = [finish get_number(line)];
     end
     line = fgetl(file_name);
    end
end

function value = get_number(charac)
    s = ',';
    cout = 4;lock = true;
    for i = 1:length(charac)
        if(charac(i) == s)
            cout = cout-1;
        elseif(cout == 0 && lock)
            lock = false;
            value = charac(i+1:end);
        end
    end
    value = str2double(value);
end
