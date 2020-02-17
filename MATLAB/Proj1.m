%----- part 1 ------
x = 10;
y = 5;

u = x > y
v = x < y ;
w = x == y ;
ww = x >= 2 * y;

if u == 1
    disp("x > y")
    y = x;
else
    disp("y > x")
    x = y;
end

%----- part 2  (Task 1)-----

T = 1; %period of 1 ms
Vm = 1; %Voltage of 1V
 
%Create a vector from 0 to 1 ms with an incremental value of .001 ms
t = linspace(0,T,1001);
len = length(t);

% v is a new vector the 1 row and len many columns
v = zeros(1 ,len);
%fill in each value of t as our function
for n = 1:len
    v(n) = (Vm - ((Vm/T)* t(n)));
end

figure(1)
plot(t,v)
title('V(t) trial 1')
xlabel('Time (ms)')
ylabel('Voltage (V)')
legend('v')
grid on

%-----(Task 2)-----
t = linspace(-T,2*T,3001);
len = length(t);

% v is a new vector the 1 row and len many columns
v = zeros(1 ,len);
%fill in each value of t as our function
for n = 1:len
    % for any values that don't correspond with values from 1 to 0
    % we throw them out
    if( t(n) >= 0)
        if (t(n) <= T)
            v(n) = (Vm - ((Vm/T)* t(n)));
        end
    else
        v(n) = 0;
    end
end

figure(2)
plot(t,v,'r')
title('V(t) trial 2')
xlabel('Time (ms)')
ylabel('Voltage (V)')
legend('v')
grid on

%-----(Task 3)-----

t = linspace(-T,2*T,3001);
len = length(t);

% v is a new vector the 1 row and len many columns
v = zeros(1 ,len);
%fill in each value of t as our function
for n = 1:len
    % instead of exluding values that are above or below our range of 1 to
    % 0 we simply replace t(n) with t(n) - t(n truncated) to always ensure 
    % we have values between 0 and 1
    v(n) = (Vm - ((Vm/T)* (t(n) - floor(t(n)))));
end


figure(3)
plot(t,v,'m')
title('V(t) trial 3')
xlabel('Time (ms)')
ylabel('Voltage (V)')
legend('v')
grid on
