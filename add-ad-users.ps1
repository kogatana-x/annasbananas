$Users = Import-Csv -Delimiter "," -Path "ad-users.csv"

foreach ($User in $Users){
    net user $User.username $User.password /add /domain
}

exit
