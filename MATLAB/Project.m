diary p2a.txt
%----------------------------------------------------------
% P2a
%
% Make some digital audio signals and demonstrate filtering.
% All signals are 4 seconds in duration.
% - Make x1 a 250 Hz pure tone.
% - Play x1 through the sound card.
% - Make x2 a swept frequency chirp from 1 kHz to 3 kHz.
% - Play x2 through the sound card.
% - Make x3 = x1 + x2.
% - Play x3 through the sound card.
% - Apply a lowpass digital Butterworth filter to x3 to
% keep the pure tone and reject the chirp.
% - Play the filtered signal through the sound card.
% - Apply a highpass digital Butterworth filter to x3 to
% keep the chirp and reject the pure tone.
% - Play the filtered signal through the sound card.
%
Fs = 44100; % sampling frequency in Hz
N = Fs * 4; % length of the 4 sec signal
n = 0:N-1; % discrete time variable
% Make x1 a 250 Hz pure tone
f_analog = 250; % pure tone analog frequency
w_dig = 2*pi*f_analog/Fs; % radian digital frequency
x1 = cos(w_dig * n); % the pure tone
sound(x1,Fs,16); % play it through sound card
pause(5); % wait for sound card to clear
% Make x2 a chirp. Sweep analog freq from 1 kHz to 3 kHz
f_start_analog = 1000;
w_start_dig = 2*pi*f_start_analog/Fs;
f_stop_analog = 3000;
w_stop_dig = 2*pi*f_stop_analog/Fs;
phi = (w_stop_dig-w_start_dig)/(2*(N-1))*(n.*n) + w_start_dig*n;
x2 = cos(phi);
sound(x2,Fs,16); % play it through sound card
pause(5); % wait for sound card to clear
% Add the two signals
x3 = x1 + x2;
x3 = x3 / max(abs(x3)); % normalize the range to [-1,1]
sound(x3,Fs,16); % play it through sound card
pause(5); % wait for sound card to clear
% Use a lowpass digital Butterworth filter to keep the 250 Hz
% pure tone and reject the chirp.
Wp = w_dig/pi; % normalized passband edge freq
Ws = w_start_dig/pi; % normalized stopband edge freq
Rp = 1; % max passband ripple
Rs = 60; % min stopband attenuation
[Nf, Wn] = buttord(Wp,Ws,Rp,Rs); % design filter order
[num,den] = butter(Nf,Wn); % design the filter
h=fvtool(num,den); % show frequency response
figure(2);
freqz(num,den,1024); % plot frequency response
title('Lowpass Frequency Response');
y1 = filter(num,den,x3); % apply the filter
y1 = y1 / max(abs(y1)); % normalize filtered signal
sound(y1,Fs,16); % play it through sound card
pause(5); % wait for sound card to clear
% Use a highpass digital Butterworth filter to keep the chirp
% and reject the 250 Hz pure tone.
Ws = w_dig/pi; % normalized stopband edge freq
Wp = w_start_dig/pi; % normalized passband edge freq
Rp = 1; % max passband ripple
Rs = 60; % min stopband attenuation
[Nf, Wn] = buttord(Wp,Ws,Rp,Rs); % design filter order
[num2,den2] = butter(Nf,Wn,'high'); % design the filter
Hd = dfilt.df1(num2,den2); % make filter object
addfilter(h,Hd); % add filter 2 to fvtool
figure(3);
freqz(num2,den2,1024); % plot frequency response
title(' Highpass Frequency Response');
title(' Highpass Frequency Response');
y2 = filter(num2,den2,x3); % apply the filter
y2 = y2 / max(abs(y2)); % normalize filtered signal
sound(y2,Fs,16); % play it through sound card
diary off