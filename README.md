# Jarvis

Voice Assistant, Voice to Action


After searching for an Customizable Voice Assistant for Linux earlier this year, my search enden at ILA: https://sourceforge.net/p/ila-voice-assistant/discussion/general/ - wich is kinda nice, but not realy what i was searching for. So i started to programm a bit myself and came up with Jarvis. I focused on an Addon-System which should allow a user to easily configure Commands and Answers for Actions and furthermore allows him/her to come up with own actions by simply programming a little Action-class. 

Sadly i had no time to finish the Job and since it's stuck for a few Month i though i should just publish what i got, and maybe there is someone out there who can use some of my Ideas.. If you do so, i would be interested to hear some words about your project ;)

Now whats working:
- simple "godmode"-GUI which shows up in the middle of the Screen. originaly i planed to add an Status-Bar-Icon so you could freely press a hotkey(/or say a hotword) to start the voice recognition and get the result either per audio or via an popup and status updates via the changing Icon.
- Yeah the Popups.. They're bit buggy right now but i figured it would be best to have some Google Now-like popup-cards instead of audio-only. Espacially because i don't like computer voices :D
- the Addon-API. i think there is still a lot to do, but the general thinks work. Like support for an own settings-view or popups.. 
- you can use parameters (static and dynamic) for your questions and answers. 
  e.g. static: pizzatimer=Time%reminder%period%m%Pizza%10 so the voice command "pizzatimer" should start an timer "Pizza" which takes 10minutes
  dynamic: calculate %ws=calculate  executes the calculate-action with the parameters after the "calculate" keyword. "%ws" stands for a parameter with spaces, e.g. "x plus two" since i process the recognised text word by word.

thats basically it. there are few action implemented yet, for example for public transport (using the grad public-transport-enabler by Andreas Schildbach https://github.com/schildbach/public-transport-enabler also known from the great Öffi-App), RSS-feeds, search, and calculus by wolframalpha. The idea was that i focus on the kernel and make a "marketplace" for different addons, so you wouldn't have to load all addons but only those you like/need. If you make an addon/action please feel free to tell me about it ;)

You will probably get some problems on first start since i developed on linux and there are some things hard-coded which wont work on Win/Mac - but in general alls should work on linux/mac/win


have fun ;)
