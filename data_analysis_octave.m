close all; clear all; warning('off','all')


%% Functions
function y_value = values_plot(arrival , start)
    y_value = zeros(1,floor(length(arrival)));
    for index = 1: length(arrival)
       value = ceil(arrival(index));
       y_value(value) = (start(index)-arrival(index));
       
       % REPLACED THE FOR LOOP BY A SINGLE LINE
       y_value(value:value+(start(index) - arrival(index))) = start(index) - arrival(index);
       %for i = value:(start(index)-arrival(index))+value
       %    y_value(i) = start(index)-arrival(index);
       %end
    end
end

%Calculates the mean of two vectors
function average = find_average(arrival, start)
    % REPLACED THE FOR LOOP BY ARRAY DIFFERENCES
    waiting_time = start - arrival;
    sum_waiting_time = sum(waiting_time);
    %for index = 1: length(arrival)
    %    waiting_time = start(index) - arrival(index);
    %    sum = sum + waiting_time;
    %end
    average = sum_waiting_time / length(arrival);
end

function [avg_sim1, performance_g_1 , performance_g_2, creation_hist, start_hist] = get_data_from_file(file_name, performance)
    line = fgetl(file_name);
    creation = []; start =[]; finish = []; avg_sim1 = []; first = false; ind = 1;
    performance_g_1= []; performance_g_2 = []; creation_hist = {}; start_hist={};
    while ischar(line)
     %Checks if there is a new simulation
     if (strfind(line, 'simulation'))
         %Checks if it's not the first 'simulation' to avoid errors
         if(first)
            %Checks the performance guarantees
            [ percentage ]= percentage_outliers(creation, start,performance);
            %Adds them to a vector
            performance_g_1 = [performance_g_1 percentage(1)];
            performance_g_2 = [performance_g_2 percentage(2)];
            % Calculate the average waiting time
            avg_sim1 = [avg_sim1 find_average(creation, start)];
            creation_hist{ind} =  creation;
            start_hist{ind} =  start;
            ind = ind+1;
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

% This function checks the percentage of 'waiting time' that are lower than
% the performance needed
function percentage =  percentage_outliers(creation_time, start_time, performance)
    n1 = length(creation_time);
    
    % REPLACED THE FOR LOOP BY THESE 3 LINES
    waiting_time = start_time - creation_time;
    iterator_guarantee_1 = sum(waiting_time <= performance(1));
    iterator_guarantee_2 = sum(waiting_time <= performance(2));
    
    %iterator_guarantee_1 = 0;
    %iterator_guarantee_2 = 0;
    %for index = 1:n1
    %    if(start_time(index)-creation_time(index) <= performance(1))
    %        iterator_guarantee_1 = iterator_guarantee_1+1;
    %        iterator_guarantee_2 = iterator_guarantee_2+1;
    %    elseif(start_time(index)-creation_time(index) <= performance(2))
    %        iterator_guarantee_2 = iterator_guarantee_2+1;
    %    end
    %end

    percentage_1 = iterator_guarantee_1/n1;
    percentage_2 = iterator_guarantee_2/n1;
    percentage = [percentage_1*100, percentage_2*100];
end

% This is a function which is useful for parsing the .txt file
% It retrieves the float value for each line of the file
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




% Load the statistics package for Octave (used only for tinv)
pkg load statistics


%% Data Analysis
%   Make sure to run the .java files first in order to generate
%   data on the .txt files

%% Performance guarantees
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Recall : -90% customers need to be assisted within 5 mins        %
%          -95% customers need to be assisted within 10 mins       %
%          -95% corporate need to be assisted within 3 mins        %
%          -99% corporate need to be assisted within 7 mins        %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
customer_per_1 = 5*60;
customer_per_2 = 10*60;
corporate_per_1 = 3*60;
corporate_per_2 = 7*60;

performance_customer = [customer_per_1, customer_per_2];
performance_corporate = [corporate_per_1, corporate_per_2];
%% First step: Data retrieval and cleaning
% Loads the files
consumer_file = fopen('MSConsumerMine.txt');
corporate_file = fopen('MSCorporateMine.txt');

% Extract the data from the files and store them in vectors
[avg_consumer, con_outliers_set_1 ,con_outliers_set_2, arr_cons, start_cons]  = get_data_from_file(consumer_file, performance_customer);
[avg_corporate, corp_outliers_set_1,corp_outliers_set_2, arr_corp, start_corp] = get_data_from_file(corporate_file, performance_corporate);

disp('-------------')

% Displays the percentage of successful trials
cn_str = ['Customer (5 mins) : Percentage of success (%%) : [', repmat('%g, ', 1, numel(con_outliers_set_1)-1),'%g]\n'];
cn_str2 = ['Customer (10 mins) : Percentage of success (%%) : [', repmat('%g, ', 1, numel(con_outliers_set_2)-1),'%g]\n'];

cp_str = ['Corporate (3 mins) : Percentage of success (%%) : [', repmat('%g, ', 1, numel(corp_outliers_set_1)-1),'%g]\n'];
cp_str2 = ['Corporate (7 mins) : Percentage of success (%%) : [', repmat('%g, ', 1, numel(corp_outliers_set_2)-1),'%g]\n'];

fprintf(cn_str, con_outliers_set_1)
fprintf(cn_str2, con_outliers_set_2)
fprintf(cp_str, corp_outliers_set_1)
fprintf(cp_str2, corp_outliers_set_2)

disp('-------------')

%% Find average waiting time and confidence interval
disp(['Average waiting time (customer): ', num2str(avg_consumer(1)),'s']);
disp(['Average waiting time (corporate): ', num2str(avg_corporate(1)),'s']);

% Confidence Interval
% Say we want 95% Confidence Interval, we consider positive the values of
% range [0.025, 0.975]
r1 = 0.025;
r2 = 0.975;

n1 = length(avg_consumer);
n2 = length(avg_corporate);

% Find the t-inverse function
%pkg load statistics;
%t_inverse_1 = tinv([r1 r2], n1-1);
%t_inverse_2 = tinv([r1 r2], n2-1);

% Find the variances of both samples
var_consumers = var(avg_consumer);
var_corporate = var(avg_corporate);

% STD of the means of both sampls
std_mean_cons = sqrt(var_consumers/n1);
std_mean_corp = sqrt(var_corporate/n2);

% Confidence Intervals
%Conf_Interv_cons = avg_consumer + t_inverse_1*std_mean_cons
%Conf_Interv_corp = avg_corp + t_inverse_2*std_mean_corp

%% Replication/ Deletion Approach
% This function uses the Welch method to find the right l vlaue
% Do multiple runs and partition our results in two clusters
% Withdraw the variables from X(1) to X(l)
% CHANGE THIS SO IT TAKES THE MEAN INSTEAD
y_history = zeros(1,length(arr_cons));
y_iterate = {};
figure('Renderer', 'painters', 'Position', [10 10 1300 600]);

%Plots each simulation's waiting time
for index = 1:length(arr_cons)
    arrival_time = arr_cons{index};
    starting_time = start_cons{index};
    y = values_plot(arrival_time , starting_time);
    y_history(index) = length(y);
    y_iterate{index} = y;
    x = linspace(1, length(y), length(y));
    subplot(2,3,index); plot(x,y); title(['Simulation: ', num2str(index)])
end

%get the max value from all the values so we can equal the vector lengths
y_max = max(y_history);

%Equalize all the vector lengths so we can apply the mean on each of them
for index = 1:length(y_iterate)
    missing_Y = y_max - y_history(index);
    for i = y_history(index)+1:missing_Y+y_history(index)
        y_iterate{index}(i) = 1;
    end
end
%Calculate the mean waiting time vector
mean_vector = zeros(1,length(y_iterate{1}));
for index = 1:length(y_iterate{1})
    sum = 0;
    for i = 1: length(y_iterate)-1
        sum = sum + y_iterate{i}(index);
    end
    mean_vector(index) = (sum/length(y_iterate));
end
% plots the mean vector
x = linspace(1, length(mean_vector), length(mean_vector));
figure('Renderer', 'painters', 'Position', [10 10 1300 600]); plot(x,mean_vector,'g');  xlim([4e4 10e4])

% Deletion factor l
l = 7e4