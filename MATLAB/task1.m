%---- task 1 -----
x = 2;
y = x*3 ^x*exp(x);


%---- task 2-----

v1 = [1; 2; 3; 4;];
v2 = [1 2 3 4];
v1 * v2
v2 * v1

v = linspace(0,12,121)
R = 4.7e3;
i = v/R;
v(89)
i(89)

figure(1)
plot(v,i)
title('I = V/R')
xlabel('Voltage (V)')
ylabel('Current (A)')
grid on