echo "Downloading main JAR..." &&
wget -q "https://github.com/theapache64/gpm/releases/latest/download/gpm.main.jar" -O "gpm.main.jar" --show-progress &&
# cp /home/theapache64/Documents/projects/gpm/gpm.main.jar gpm.main.jar &&

echo "Downloading autocompletion script..." &&
wget -q "https://github.com/theapache64/gpm/releases/latest/download/gpm_completion" -O "gpm_completion" --show-progress &&

echo "Moving files to ~/.gmp" &&

mkdir -p ~/.gpm &&
mv gpm.main.jar ~/.gpm/gpm.main.jar &&
mv gpm_completion ~/.gpm/gpm_completion &&

echo "Installing..." &&
echo "alias gpm='java -jar ~/.gpm/gpm.main.jar'" >> ~/.bashrc &&
echo ". ~/.gpm/gpm_completion" >> ~/.bashrc &&

echo "Done"
