%----------------------------------------------------------
% P2b
%
% Make some digital audio signals and demonstrate filtering.
% All signals are 4 seconds in duration.
% - Make x1 a 1 kHz cos tone.
% - Play x1 through the sound card.
% - Make x2 a 3 kHz cps tone.
% - Play x2 through the sound card.
% - Make x3 = x1 + x2.
% - Play x3 through the sound card.
% - Apply a lowpass digital Butterworth filter to x3 to
% keep the pure tone and reject the chirp. 1dB max pass ripple
% 60dB min atthen
% - Play the filtered signal through the sound card.
%
Fs = 44100; % sampling frequency in Hz
N = Fs * 4; % length of the 4 sec signal
n = 0:N-1; % discrete time variable
% Make x1 a 1000 Hz pure tone
f_analog = 1000; % pure tone analog frequency
w_dig = 2*pi*f_analog/Fs; % radian digital frequency
x1 = cos(w_dig * n); % the pure tone
sound(x1,Fs,16); % play it through sound card
pause(5); % wait for sound card to clear
% Make x2 a 3000 hz pure tone.
f_start_analog = 3000;
w_start_dig = 2*pi*f_start_analog/Fs;
x2 = cos(w_start_dig * n);%pure tone
sound(x2,Fs,16); % play it through sound card
pause(5); % wait for sound card to clear
% Add the two signals
x3 = x1 + x2;
x3 = x3 / max(abs(x3)); % normalize the range to [-1,1]
sound(x3,Fs,16); % play it through sound card
pause(5); % wait for sound card to clear
% Use a lowpass digital Butterworth filter to keep the 1000 Hz
% pure tone and reject the chirp.
Wp = w_dig/pi; % normalized passband edge freq
Ws = w_start_dig/pi; % normalized stopband edge freq
Rp = 1; % max passband ripple
Rs = 60; % min stopband attenuation
[Nf, Wn] = buttord(Wp,Ws,Rp,Rs); % design filter order
[num,den] = butter(Nf,Wn); % design the filter
y1 = filter(num,den,x3); % apply the filter
y1 = y1 / max(abs(y1)); % normalize filtered signal
sound(y1,Fs,16); % play it through sound card
pause(5); % wait for sound card to clear
