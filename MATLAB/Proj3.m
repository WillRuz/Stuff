% --- ex 1 -------------------------------------------------------------
t = linspace(-5,5,101);
u1 = heaviside(t);
u2 = heaviside(t - 1);
u3 = heaviside(t - 2);

%plot these 3 functions in a 3x1
figure(1)
subplot(3,1,1)
plot(t,u1,'r')
title('u1 plot')
xlabel('t')
ylabel('value')
legend('u1')
grid
subplot(3,1,2)
plot(t,u2,'g')
title('u2 plot')
xlabel('t')
ylabel('value')
legend('u2')
grid
subplot(3,1,3)
plot(t,u3,'b')
title('u3 plot')
xlabel('t')
ylabel('value')
legend('u3')
grid

%----- functions of u1,u2,u3 
y1 = u1 - u2;
y2 = u1 + u2 + u3;
y3 = u1 .* u3;
y4 = u1 + u2 - 2*u3;

%plot these 4 functions in a 2x2
figure(2)
subplot(2,2,1)
plot(t,y1,'r')
title('y1 plot')
xlabel('t')
ylabel('value')
legend('y1')
grid
subplot(2,2,2)
plot(t,y2,'g')
title('y2 plot')
xlabel('t')
ylabel('value')
legend('y2')
grid
subplot(2,2,3)
plot(t,y3,'b')
title('y3 plot')
xlabel('t')
ylabel('value')
legend('y3')
grid
subplot(2,2,4)
plot(t,y4,'c')
title('y4 plot')
xlabel('t')
ylabel('value')
legend('y4')
grid

% --- ex 2 --------------------------------------------------------------
t = linspace(-2,2,41);
u = heaviside(t);
v0 =  cos(2 * pi * t) .* u ;
v1 = u .* cos(2 * pi * t + pi/4);
v2 = u .* cos(.5 * pi * t);
v3 = v0 .* v2 ;
v4 = (5 * exp(-2*t)) .* v0 ;

%plot these 4 functions in a 2x2
figure(3)
subplot(2,2,1)
plot(t,v0,'r')
title('v0 vs. v1 plot')
xlabel('t')
ylabel('value')
hold on 
plot(t,v1,'b')
hold off
legend('v0', 'v1')
grid

subplot(2,2,2)
plot(t,v0,'r')
title('v0 vs. v2 plot')
xlabel('t')
ylabel('value')
hold on 
plot(t,v2,'g')
hold off
legend('v0', 'v2')
grid

subplot(2,2,3)
plot(t,v2,'g')
title('v2 vs. v3 plot')
xlabel('t')
ylabel('value')
hold on 
plot(t,v3,'y')
hold off
legend('v2', 'v3')
grid

subplot(2,2,4)
plot(t,v0,'r')
title('v0 vs. v4 plot')
xlabel('t')
ylabel('value')
hold on 
plot(t,v1,'c')
hold off
legend('v0', 'v4')
grid

% --- ex 3 --------------------------------------------------------------

H1s = tf([1],[1 1]);
H2s = tf([2 3 2], [3 0 2 1]);

%multiply these two transfer functions to make a magic function should be

%       2 s^2 + 3 s + 2
%  -------------------------------
%  3 s^4 + 3 s^3 + 2 s^2 + 3 s + 1

Magic = H1s .* H2s

% --- ex 4 -------------------------------------------------------------
F1 = [1 5 6];
poly2str(F1, 's');

%value at s = 1 : should be 12
polyval(F1, 1)

%factored polynomial : roots should be -2 and -3
roots(F1)

%making a polynomial with 5 roots.
F2 = poly([-1+2j -1-2j -1 0 0])

H = conv(F1,F2)
%   Hcheck ...   (s^5 + 3s^4 + 7s^3 + 5s^2) x (s^2 + 5s + 6)
% = s^7 + 3s^6 + 7s^5 + 5s^4  
%       + 5s^6 + 15s^5+ 35s^4 + 25s^3
%              + 6s^5 + 18s^4 + 42s^3 + 30s^2
% = 1       8     28    58       67      30     0     0 Math checks out ?

% ---- part 2 ------

n = [1 1];
d = [1 4 0];

%create a tf using numerator n and denominator d
H3s = tf(n ,d)

[r,p,k] = residue(n,d)

% .75/(s+4)  + .25/s  via residue 
% using a laplace table we transfrom H3(s) = (.75e^(-4t) + .25)u(t)
n2 = [1 0 4];
d2 = [1 4 0 0];
H4s = tf(n2,d2);
[r,p,k] = residue(n2,d2);
%H4s = 1.25/s+4 + -.25/s + 1/s^2

n3 = [4 1 0 4];
d3 = poly([0 0 0 -3 -3 sqrt(2)*1j -sqrt(2)*1j]);
H4s = tf(n3,d3)
%           4 s^3 + s^2 + 4
%  --------------------------------------
%  s^7 + 6 s^6 + 11 s^5 + 12 s^4 + 18 s^3

%now to build a model that will work with ilaplace ... tf doesnt work
syms s
arrayN = poly2sym(n3,s);
arrayD = poly2sym(d3,s);

H4Laplace = ilaplace(arrayN/arrayD)
% Gives this ugly mothajamma
% (493*exp(-3*t))/3267 - (4*t)/27 - (41*cos(2^(1/2)*t))/242 + 
% (95*t*exp(-3*t))/297 + (17*2^(1/2)*sin(2^(1/2)*t))/121 + t^2/9 + 1/54

%now lets graph this function in the t-domain and the s-domain

figure(4)
impulse(H4s)
grid

figure(5)
bode(H4s)
grid