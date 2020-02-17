term4 = 0;
%term 4
for k = 0:2000
    ak = 4*V / (pi * (k^2 -4));
    if mod(k,2)== 1
        term4 = term4 + ao + (ak * cos(x * w));
    end
end 
term4 = term4 + bk2* sin(x * w);
n4Max = ((term4- Vx)/V)*100