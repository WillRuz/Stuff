%------task 1 ---------------
% use our first pbola file
pbola(-1)
pbola(pbola(-1))


t = linspace(-1,2,30001);

figure(7)
plot(t,pbola(t))
grid
title('pbola from -1 to 2')
xlabel('t')
ylabel('value')
legend('pbola(t)')

%graph 1
figure(1)
plot(t,pbola(t),'r')
grid
hold on
plot(t,pbola(pbola(t)),'g')
title('fn vs fn(fn) plot')
xlabel('t')
ylabel('value')
legend('fn(t)','fn(fn(t))')
hold off

t = linspace(0,5,50001);
% using the logInd1/2 , we create a  array of logicals. so 1 and 0
% slope.* logInd1 .* logInd2 ... A.K.A if any value is false,
% when a value isnt between 4 and 1, it will be 0
logInd1 = t <= 4;
logInd2 = t >= 1;
y1 = slope(1, t, -2);
y2 = slope(-1, t, 2);

%graph 2
figure(2)
plot(t,y1 .* logInd1 .* logInd2,'r')
grid
hold on
plot(t,y2 .* logInd1 .* logInd2,'g')
title('x-2 vs -x+2 plot (from 1 to 4)')
xlabel('t')
ylabel('value')
legend('x - 2','-x + 2')
hold off

%next graph lies between -1<= x <= 5
x = linspace(-1,5,60001);
% (x1)2x - x.^2 intersects with -x +2 at x = 1.. fn = 2x - x.^2
cond11 = 0 <= x;
cond12 = x < 1;
part1 = f3(-1,2,0,x) .* cond11 .* cond12;
% (x2)-x +2 intersects with x -2 at x = 2.. fn = -x + 2
cond21 = 1 <= x;
cond22 = x < 2;
part2 = slope(-1,x,2) .* cond21 .* cond22;
% (x3)x - 2 intersects with 6x - x.^2 -8 at x = 3 .. fn = x -2
cond31 = 2 <= x;
cond32 = x < 3;
part3 = slope(1,x,-2) .* cond31 .* cond32;
% finally the function ends at x <= 4.. fn = 6x -x.^2 - 8
cond41 = 3 <= x;
cond42 = x <= 4;
part4 = f3(-1,6,-8,x)  .* cond41 .* cond42;

%add all the parts up to get a function
fnc3 = part1 + part2 + part3 + part4;
maximum = max(fnc3);

figure(3)
plot(x,fnc3,'b')
title('y3(x)')
axis([-1 5 -1 2*maximum])
xlabel('t')
ylabel('value')
legend('y3')

%---------------- task 2 --------------------------------
figure(4)
f0 = 10000;
B = 5000;
C = 1 * 10.^(-6);
L = 1/((2*pi*f0)^2*C);
R = 1/(B*C);
w0 = 2*pi*f0;
fc1=-(B/2)+sqrt((B/2)^2+f0^2);
fc2=(B/2)+sqrt((B/2)^2+f0^2);

f=fc1*100:fc2*100;
H=(B*1i*2*pi.*f)./((1i*2*pi.*f).^2+B*1i*2*pi.*f+w0^2);
H = tf([0 1/R*C 0],[1 1/R*C 1/L*C]);
bode(H)
bandpass((10^(-6)),5000,10000)
