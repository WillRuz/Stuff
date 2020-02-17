%--- ECE 3793, Fall 2019 Matlab Homework #1: Will Ruzicka

%--------- #1 ----------

%t is our time array
t = linspace(-1,5,601);
%fn is initially all zeros, but we will fill that with values
fn = zeros(1,601);

%fn1 has 2 conditions
cond11 = 0 < t;
cond12 = t <= 4;
fn = 1 .* cond11 .* cond12;

figure(1)
plot(t,fn,'r')
axis([-1 5 -2 2])
xticks([0 2 4])
yticks([-1 0 1])
grid
title('fn1 from -1 to 5 seconds')
xlabel('t(seconds)')
ylabel('x1(t)')
legend('x1(t)')


%for the second function, there are many conditons

%for 0 < t < 1 , fn = 1
cond21 = 0 < t;
cond22 = t < 1;
part1 = 1 .* cond21 .* cond22;

%for 1 <= t < 3,  fn = -1
cond23 = 1 <= t;
cond24 = t < 3;
part2 = -1 .* cond23 .* cond24;

% for 3 <= t < 4, fn = 1
cond25 = 3 <= t;
cond26 = t < 4;
part3 = 1 .* cond25 .* cond26;

fn = part1 + part2 + part3;

figure(2)
plot(t,fn,'g')
axis([-1 5 -2 2])
xticks([0 2 4])
yticks([-1 0 1])
grid
title('fn2 from -1 to 5 seconds')
xlabel('t(seconds)')
ylabel('x2(t)')
legend('x2(t)')

% --------- #2 ------------

% user defined variables t1,t2, and s
s = .7;
t1 = -1 + j;
t2 = 2;

%lets define our time function from -5 to 5
t = linspace(-5,5,10001);

%define unitstep
% if I wanted u(t) unitstep(t>=0) = 1;
unitstept1 = zeros(size(t));

%since its u(t - t1), we shift from 0 to the right by t1
unitstept1(t>0 + t1) = 1;

%define u(t-t2)
unitstept2 = zeros(size(t));
unitstept2(t>(0 + t2)) = 1;

%define u(t-t1) - u(t-t2)
lt = unitstept1 - unitstept2;

%define e^st[lt]
xt = exp(s.*t).*lt;

figure(3)
plot(t,real(xt),'m')
grid
hold on
plot(t,imag(xt),'b')
title('x(t) real vs. imaginary')
xlabel('t(seconds)')
ylabel('x(t)')
legend('x(t) real', 'x(t) imaginary')
hold off

% ----------- #3 ----------
%create our needed variables
%im defining n from 0 to 4pi in 40 increments
n = linspace(0,100,101);
x1 = cos(0.2*pi.*n);
x2 = cos(2.2*pi.*n);

%define unitstep
unitstep = zeros(size(n));
unistep(n>0) = 1;
%since its u(t - 20), we shift from 0 to the right by 20
unitstepx = zeros(size(n));
unitstepx(n>20) = 1;

%u(n) - u(n-20)
unitx = unitstep - unitstepx;

%now we can make our functions x1n and x2n
x1n = x1 .* unitx;
x2n = x2 .* unitx;

%lets plot

figure(4)
subplot(2,1,1)
stem(n,x1n)
title('x1[n]')
xlabel('n')
ylabel('value')
grid
subplot(2,1,2)
stem(n,x2n)
title('x2[n]')
xlabel('n')
ylabel('value')
grid


