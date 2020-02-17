%----------band pass----------
function bandpass(C, B, f0)

%-----fill our constants-----------
L = 1/((2*pi*f0)^2*C)
R = 1/(B*C)
w0 = 2*pi*f0
fc1=-(B/2)+sqrt((B/2)^2+f0^2)
fc2=(B/2)+sqrt((B/2)^2+f0^2)

f=fc1*100:fc2*100;
H=(B*1i*2*pi.*f)./((1i*2*pi.*f).^2+B*1i*2*pi.*f+w0^2);

%----------create plot-------------
subplot(2,1,1)
semilogx(f,20.*log10(abs(H))); 
xlabel('f');
ylabel('|H| (dB)');
title('Magnitude of Bandpass');
legend({'|H(s)|'},'Location','northwest')

subplot(2,1,2)
semilogx(f,angle(H).*180./pi);
xlabel('f');
ylabel('angle');
title('Phase of Bandpass');
legend({'Angle of |H(s)|'},'Location','northwest')

end