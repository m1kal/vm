(ns vm.text)

(def english {:op-set "set"
              :op-inc "inc"
              :op-dec "dec"
              :op-copy "copy"
              :op-eval "eval"
              :op-goto "goto"
              :op-setop "setop"
              :op-if "if-zero"
              :registers "Registers"
              :vm "Virtual Machine"
              :run "Run"
              :program "Program:"
              :execute "Single step"
              :reset "Reset program memory"
              :restart "Restart program"
              :help [:div {:style {:position :absolute :top "0px" :left "600px"}}
                     "Instructions:"
                     [:ul
                      [:li "set X Y - sets register X to value Y"]
                      [:li "copy X Y - sets register Y to the value of register X"]
                      [:li "inc X - increments register X by 1"]
                      [:li "dec X - decrements register X by 1"]
                      [:li "setop X - sets operation to X. Supported values: +, -, *, /"]
                      [:li "goto X - jumps to instruction X"]
                      [:li "eval - runs the operation on registers 1 and 2, the result is stored in register 8"]
                      [:li "if-zero X [instr] - runs instr if register X is zero"]]]})

(def polish {:op-set "ustaw"
             :op-inc "zwiększ"
             :op-dec "zmniejsz"
             :op-copy "kopiuj"
             :op-eval "oblicz"
             :op-goto "skocz"
             :op-setop "działanie"
             :op-if "jeśli-zero"
             :registers "Rejestry"
             :vm "Maszyna wirtualna"
             :run "Uruchom"
             :program "Program:"
             :execute "Wykonaj jedną instrukcję"
             :reset "Wyczyść pamięć programu"
             :restart "Uruchom od nowa"
             :help [:div {:style {:position :absolute :top "0px" :left "600px"}}
                    "Instrukcje:"
                    [:ul
                     [:li "ustaw X Y - ustawia rejestr X na wartość Y"]
                     [:li "kopiuj X Y - ustawia rejestr Y na wartość rejestru X"]
                     [:li "zwiększ X - zwiększa rejestr X o 1"]
                     [:li "zmniejsz X - zmniejsza rejestr Y o 1"]
                     [:li "działanie X - Ustawia działanie X. Dopuszczalne działania: +, -, *, /"]
                     [:li "skocz X - skacze do instrukcji X"]
                     [:li "oblicz - wykonuje działanie na rejestrach 1 i 2, wynik ląduje w rejestrze 8"]
                     [:li "jeśli-zero X [instr] - wykonuje instr jeśli rejestr X jest równy zeru"]]]})
