%-------- task 1 ---------
V = 1;
T = 1;

x = linspace(-T,3*T,40001);
%First condition
cond11 = 0 <= mod(x,T);
cond12 = mod(x,T) <= T/2;
fn1 = V * sin(4*pi*x/T) .* cond11 .* cond12;

%2nd Condition
cond21 = T/2 <= mod(x,T);
cond22 = mod(x,T) <= T;
fn2 = 2*V * sin(4*pi*x/T) .* cond21 .* cond22;

%add both conditions to make Vx ... add x for our linspahce
Vx = fn1 + fn2;
w = 4 * pi / T;

figure(1)
plot(x,Vx)
title('Plot of Vx')
legend('Vx')
xlabel('t')
ylabel('value')


%------ 2nd task -----

%ak = 4*V / (pi * (k^2 -4));
bk2 = 3/2;
ao = 0;

%term 1
term1 = 0;
for k = 0:1
    ak = 4*V / (pi * (k^2 -4));
    term1 = term1 + ao + (ak * cos(x *w));
end 

%term 2
term2 = 0;
for k = 0:2
    ak = 4*V / (pi * (k^2 -4));
    if mod(k,2)== 1
        term2 = term2 + ao + (ak * cos(x *w));
    end
end 
%for all terms k >= 2, you must add in the bk2 term
term2 = term2 + (bk2* sin(x * w));

%term3
term3 = 0;
for k = 0:3
    ak = 4*V / (pi * (k^2 -4));
    if mod(k,2)== 1
        term3 = term3 + ao + (ak * cos(x * w));
    end
end 
term3 = term3 + (bk2* sin(x * w));

%term4 - Bear in mind since there is no change in ak or bk at t =4
term4 = 0;
%term 1
for k = 0:4
    ak = 4*V / (pi * (k^2 -4));
    if mod(k,2)== 1
        term4 = term4 + ao + (ak * cos(x * w));
    end
end 
term4 = term4 + (bk2* sin(x * w));

figure(2)
subplot(2,2,1)
plot(x,term1,'r')
title('term 1 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=1', 'Vx')
grid

subplot(2,2,2)
plot(x,term2,'r')
title('term 4 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=2', 'Vx')
grid

subplot(2,2,3)
plot(x,term3,'r')
title('term 3 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=3', 'Vx')
grid

subplot(2,2,4)
plot(x,term4,'r')
title('term 4 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=4', 'Vx')
grid

%----- task 3, same thing as last task... just use n = 3,5,10,50-------
%since I decided not to use functions, and simply retype my code, this will
%be fun


%term 2
term1 = 0;
for k = 0:3
    ak = 4*V / (pi * (k^2 -4));
    if mod(k,2)== 1
        term2 = term2 + ao + (ak * cos(x * w));
    end
    error1 = (abs(term1- Vx)/V);
end 
%for all terms k >= 2, you must add in the bk2 term
term1 = term1 + (bk2* sin(x * w));

%term 2
term2 = 0;
for k = 0:5
    ak = 4*V / (pi * (k^2 -4));
    if mod(k,2)== 1
        term2 = term2 + ao + (ak * cos(x * w));
    end
    error2 = (abs(term2- Vx)/V);
end 
%for all terms k >= 2, you must add in the bk2 term
term2 = term2 + (bk2* sin(x * w));

%term3
term3 = 0;
for k = 0:10
    ak = 4*V / (pi * (k^2 -4));
    if mod(k,2)== 1
        term3 = term3 + ao + (ak * cos(x * w));
    end
    error3 = (abs(term3- Vx)/V);
end 
term3 = term3 + (bk2* sin(x * w));

term4 = 0;
%term 4
for k = 0:50
    ak = 4*V / (pi * (k^2 -4));
    if mod(k,2)== 1
        term4 = term4 + ao + (ak * cos(x * w));
    end
    error4 = (abs(term4- Vx)/V);
end 
term4 = term4 + (bk2* sin(x * w));

figure(3)
subplot(2,2,1)
plot(x,term1,'r')
title('k = 3 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=3', 'Vx')
grid

subplot(2,2,2)
plot(x,term2,'r')
title('k = 5 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=5', 'Vx')
grid

subplot(2,2,3)
plot(x,term3,'r')
title('k = 10 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=10', 'Vx')
grid

subplot(2,2,4)
plot(x,term4,'r')
title('k = 50 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=50', 'Vx')
grid
 %------- now we plot error.
 
n1Max = max(((term1- Vx)/V)*10);
n2Max = max(((term2- Vx)/V)*10);
n3Max = max(((term3- Vx)/V)*10);
n4Max = max(((term4- Vx)/V)*10);

figure(4)
subplot(2,2,1)
plot(x,error1,'r')
title('k = 3 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=3', 'Vx')
grid

subplot(2,2,2)
plot(x,error2,'r')
title('k = 5 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=5', 'Vx')
grid

subplot(2,2,3)
plot(x,error3,'r')
title('k = 10 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=10', 'Vx')
grid

subplot(2,2,4)
plot(x,error4,'r')
title('k = 50 plot')
xlabel('t')
ylabel('value')
hold on 
plot(x,Vx,'b')
hold off
legend('k=50', 'Vx')
grid

term4 = 0;
%term 4
for k = 0:50
    ak = 4*V / (pi * (k^2 -4));
    if mod(k,2)== 1
        term4 = term4 + ao + (ak * cos(x * w));
    end
end 
term4 = term4 + bk2* sin(x * w);
n4Max = max(((term4- Vx)/V)*10)