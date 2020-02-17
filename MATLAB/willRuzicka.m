%------ part1 ------------
%create our linespace from -5 to 5 with 1000 samples
t1 = linspace(-5,5,1000);
%create our step/piecewise function using variable t1
k1 = @(t1) (1) .* (t1>0) + (0) .* (t1 <0);
%u1 is k1 when t1 is untampered
u1 = k1(t1);
%u2 is the timeshifted funtion of u1 by 1
u2 = k1(t1 -1);
% u3 is timeshifted to the right by 2
u3 = k1(t1 -2);
%now we have our 4 y functions of u1,u2,u3
y1 = u1 - u2;
y2 = u1 + u2 + u3;
y3 = u1 .* u3;
y4 = u1 + u2 - (2 .* u3);

%--figure 1
figure(1)
subplot(3,1,1);
plot(t1,u1)
title('U1')

subplot(3,1,2);
plot(t1,u2)
title('U2')

subplot(3,1,3);
plot(t1,u3)
title('U3')

%---figure 2

figure(2)
subplot(2,2,1)
plot(t1,y1)
title('Y1')

subplot(2,2,2)
plot(t1,y2)
title('Y2')

subplot(2,2,3)
plot(t1,y3)
title('Y3')

subplot(2,2,4)
plot(t1,y4)
title('Y4')

% ----- part 2 -----------

%create our linespace from -2 to 2 with 1000 samples
t2 = linspace(-2,2,1000);
%create our 4 vectors with the linespace of t2
v0 = cos(2* pi .* t2);
v1 = cos(2* pi .* t2 + pi / 4);
v2 = cos(.5 * pi .* t2);
v3 = v0 .* v2;
v4 = 5 * exp(-2 .* t2) .* v0;

%-figure 3
figure(3)
subplot(2,2,1)
plot(t2,v0,t2,v1)
title('v0 & v1')

subplot(2,2,2)
plot(t2,v0,t2,v2)
title('v0 & v2')

subplot(2,2,3)
plot(t2,v0,t2,v3)
title('v0 & v3')

subplot(2,2,4)
plot(t2,v0,t2,v4)
title('v0 & v4')
% ----- part 3 -----------
%create our 3 transfer functions
h1s = tf([1], [1 1])
h2s = tf([2 3 2], [1 0 2 1])

h3s = h1s .* h2s
% should be (2 s^2 + 3 s + 2)/(s^4 + s^3 + 2 s^2 + 3 s + 1)

% ----- part 4 -----------
% --------- 4.1---------
%create our first vector
F1 = [1 5 6];
% look at its polynomial
poly2str(F1,'s');
% should be s^2 + 5s + 6

% declare its value at S=1 as F1_1
F1_1 = polyval(F1, 1)
% should be (1)^2 + 5(1) + 6 => 12

%declare F1's roots as F1roots
F1roots = roots(F1)
% (s + 2)(s + 3)... roots should be -2 and -3

% Using the 5 roots in (s+1?2j)(s+1+2j)(s+1)s^2 we create F2
F2 = poly([-1+2j -1-2j -1 0 0]);
% should be  s^5 + 3 s^4 + 7 s^3 + 5 s^2... or 1 3 7 5 0 0 
% its polynomial is declared as F2str
F2str = poly2str(F2,'s')

%finally we convolve our initial 2 Vectors and declare it as H.
H = conv(F1,F2)

% --------- 4.2---------

% create variables n and d as the num/denominator of H3(s)
n = [1 1];
d = [1 4 0];
% using tf we create h3
h3 = tf(n,d)


[r,p,k] = residue(n,d);
% fraction expanstion should be (.75/s + 4) + (.25 /s)
% =>  r1 = .75,r2 = .25,p1 = -4,p2=0

tt = linspace(-1,3,1000);
% from -1 to 3 in 1000 samples we inverse laplace transfrom h3

%using residue to inverse laplace our function
[b,a]=residue(n,d);
temp=0;
syms s t
for i=1:numel(a)
temp=temp+(b(i)/(s+a(i)));
end
ilh3 = ilaplace(temp,t)
%--- should be (3*exp(-4*t))/4 + 1/4 

%now replace all t values with tt to generate values to plot
syms t
ih3 = subs(ilh3,tt);

%--figure 4
figure(4)
plot(tt,ih3)
title('Inverse Laplace of h3')


%redefine our n and d to create h4
n = [1 0 4];
d = [1 4 0 0];

%fraction expansion of n and d is r p k
[r,p,k] = residue(n,d);
h4 = tf(n,d)

%using residue to inverse laplace our function
[b,a]=residue(n,d);
temp=0;
syms s t
for i=1:numel(a)
temp=temp+(b(i)/(s+a(i)));
end
ilh4 = ilaplace(temp,t)
%--- should be t + (5*exp(-4*t))/4 - 1/4

%now replace all t values with tt to generate values to plot
syms t
ih4 = subs(ilh4,tt);

%figure 5

figure(5)
plot(tt,ih4)
title('Inverse Laplace of h4')